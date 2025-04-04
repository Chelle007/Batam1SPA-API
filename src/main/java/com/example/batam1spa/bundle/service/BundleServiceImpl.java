package com.example.batam1spa.bundle.service;

import com.example.batam1spa.bundle.dto.*;
import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.bundle.model.BundleDetail;
import com.example.batam1spa.bundle.model.BundleDescription;
import com.example.batam1spa.bundle.repository.BundleDescriptionRepository;
import com.example.batam1spa.bundle.repository.BundleDetailRepository;
import com.example.batam1spa.bundle.repository.BundleRepository;
import com.example.batam1spa.common.model.LanguageCode;
import com.example.batam1spa.common.service.ValidationService;
import com.example.batam1spa.service.repository.ServicePriceRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.service.model.ServicePrice;
import com.example.batam1spa.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BundleServiceImpl implements BundleService {
    private final RoleSecurityService roleSecurityService;
    private final ValidationService validationService;
    private final BundleRepository bundleRepository;
    private final BundleDescriptionRepository bundleDescriptionRepository;
    private final BundleDetailRepository bundleDetailRepository;
    private final ServicePriceRepository servicePriceRepository;

    @Override
    public void seedBundle() {
        createBundleIfNotExists("Head & Body - 2h", "headAndBody.png", 750000, 800000, true);
        createBundleIfNotExists("Foot & Body - 2h", "footAndBody.png", 950000, 1000000, true);
        createBundleIfNotExists("Full Body Relax - 1.5h", "fullBodyRelax.png", 650000, 700000, true);
        createBundleIfNotExists("Hot Stone Therapy - 2h", "hotStoneTherapy.png", 1200000, 1300000, true);
        createBundleIfNotExists("Aromatherapy Massage - 1h", "aromatherapy.png", 550000, 600000, false);
        createBundleIfNotExists("Deep Tissue Massage - 1.5h", "deepTissue.png", 850000, 900000, true);
        createBundleIfNotExists("Foot Reflexology - 1h", "footReflexology.png", 500000, 550000, false);
    }

    private void createBundleIfNotExists(String name, String imgUrl, int localPrice, int touristPrice, boolean isPublished) {
        boolean bundleExists = bundleRepository.existsByName(name);

        if (bundleExists) {
            log.info("{} already exists in the system", name);
            return;
        }

        Bundle bundle = Bundle.builder()
                .name(name)
                .imgUrl(imgUrl)
                .localPrice(localPrice)
                .touristPrice(touristPrice)
                .isPublished(isPublished)
                .build();

        bundleRepository.save(bundle);
        log.info("{} has been added to the system", name);
    }

    @Override
    public List<BundleDTO> getAllBundle(User user) {
        // Ensure only admins can access this API
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        // Fetch all bundles
        List<Bundle> bundles = bundleRepository.findAll();

        return bundles.stream().map(bundle -> {
            // Fetch all details for this bundle
            List<BundleDetail> bundleDetails = bundleDetailRepository.findByBundle(bundle);

            // Compute total duration & bundle content
            int totalDuration = 0;
            int quantity = 0;
            Map<String, Integer> bundleContent = new LinkedHashMap<>(); // Preserve order

            for (BundleDetail detail : bundleDetails) {
                ServicePrice servicePrice = detail.getServicePrice();
                String serviceName = servicePrice.getService().getName();
                int duration = servicePrice.getDuration();
                quantity = detail.getQuantity(); // All services in the bundle have the same quantity

                totalDuration += duration;

                // Store serviceName → duration
                bundleContent.put(serviceName, duration);
            }

            return BundleDTO.builder()
                    .bundleId(bundle.getId())
                    .bundleName(bundle.getName())
                    .totalDuration(totalDuration)
                    .quantity(quantity)
                    .bundleContent(bundleContent)
                    .imgUrl(bundle.getImgUrl())
                    .localPrice(bundle.getLocalPrice())
                    .touristPrice(bundle.getTouristPrice())
                    .isPublished(bundle.isPublished())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    public BundleDTO addBundle(User user, CreateBundleDTO createBundleDTO) {
        // Ensure only admins can add a bundle
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        validationService.validatePrice(createBundleDTO.getLocalPrice(), createBundleDTO.getTouristPrice());

        // Step 1: Create and save the bundle entity
        Bundle bundle = bundleRepository.save(Bundle.builder()
                .name(createBundleDTO.getBundleName())
                .imgUrl(createBundleDTO.getImgUrl())
                .localPrice(createBundleDTO.getLocalPrice())
                .touristPrice(createBundleDTO.getTouristPrice())
                .isPublished(createBundleDTO.isPublished())
                .build());

        // Step 2: Process bundleContent and save details
        int totalDuration = 0;
        Map<String, Integer> bundleContentMap = createBundleDTO.getBundleContent();
        List<BundleDetail> bundleDetails = new ArrayList<>();
        Map<String, Integer> savedBundleContent = new LinkedHashMap<>();

        for (Map.Entry<String, Integer> entry : bundleContentMap.entrySet()) {
            String serviceName = entry.getKey();
            int duration = entry.getValue();

            // Validate if service exists
            Optional<ServicePrice> servicePriceOpt = servicePriceRepository
                    .findByServiceNameAndDuration(serviceName, duration);

            if (servicePriceOpt.isEmpty()) {
                throw new RuntimeException("Service price not found for " + serviceName + " with duration " + duration);
            }

            ServicePrice servicePrice = servicePriceOpt.get();
            totalDuration += servicePrice.getDuration();

            // Save bundle detail
            bundleDetails.add(BundleDetail.builder()
                    .bundle(bundle)
                    .servicePrice(servicePrice)
                    .quantity(createBundleDTO.getQuantity())
                    .build());

            // Add to saved bundleContent map
            savedBundleContent.put(serviceName, servicePrice.getDuration());
        }

        // Save all bundle details
        bundleDetailRepository.saveAll(bundleDetails);

        // Step 3: Return the saved bundle as a DTO
        return BundleDTO.builder()
                .bundleId(bundle.getId())
                .bundleName(bundle.getName())
                .totalDuration(totalDuration)
                .quantity(createBundleDTO.getQuantity())
                .bundleContent(savedBundleContent)
                .imgUrl(bundle.getImgUrl())
                .localPrice(bundle.getLocalPrice())
                .touristPrice(bundle.getTouristPrice())
                .isPublished(bundle.isPublished())
                .build();
    }

    @Override
    @Transactional
    public BundleDTO editBundle(User user, UUID bundleId, EditBundleDTO editBundleDTO) {
        // Step 1: Ensure only admins can edit a bundle
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        // Step 2: Find the existing bundle by ID
        Bundle existingBundle = bundleRepository.findById(bundleId)
                .orElseThrow(() -> new RuntimeException("Bundle not found with id: " + bundleId));

        // Step 3: Update the bundle details (name, image URL, prices, descriptions)
        if (editBundleDTO.getBundleName() != null) {
            existingBundle.setName(editBundleDTO.getBundleName());
        }
        if (editBundleDTO.getImgUrl() != null) {
            existingBundle.setImgUrl(editBundleDTO.getImgUrl());
        }
        validationService.validatePrice(editBundleDTO.getLocalPrice(), editBundleDTO.getTouristPrice());
        existingBundle.setLocalPrice(editBundleDTO.getLocalPrice());
        existingBundle.setTouristPrice(editBundleDTO.getTouristPrice());

        // Step 4: Handle descriptions (ID and EN)
        // Updates (add) to the BundleDescription as well
        List<BundleDescription> bundleDescriptions = new ArrayList<>();

        if (editBundleDTO.getIDDescription() != null) {
            bundleDescriptions.add(BundleDescription.builder()
                    .bundle(existingBundle)
                    .languageCode(LanguageCode.ID)  // Use LanguageCode enum
                    .description(editBundleDTO.getIDDescription())
                    .build());
        }

        if (editBundleDTO.getENDescription() != null) {
            bundleDescriptions.add(BundleDescription.builder()
                    .bundle(existingBundle)
                    .languageCode(LanguageCode.EN)  // Use LanguageCode enum
                    .description(editBundleDTO.getENDescription())
                    .build());
        }

        if (editBundleDTO.getIDIncludedItemDescription() != null) {
            bundleDescriptions.add(BundleDescription.builder()
                    .bundle(existingBundle)
                    .languageCode(LanguageCode.ID)  // Use LanguageCode enum
                    .description(editBundleDTO.getIDIncludedItemDescription())
                    .build());
        }

        if (editBundleDTO.getENIncludedItemDescription() != null) {
            bundleDescriptions.add(BundleDescription.builder()
                    .bundle(existingBundle)
                    .languageCode(LanguageCode.EN)  // Use LanguageCode enum
                    .description(editBundleDTO.getENIncludedItemDescription())
                    .build());
        }

        if (!bundleDescriptions.isEmpty()) {
            bundleDescriptionRepository.saveAll(bundleDescriptions);
        }

        // Step 5: Save the updated bundle
        bundleRepository.save(existingBundle);

        // Return the updated bundle as a DTO
        return BundleDTO.builder()
                .bundleId(existingBundle.getId())
                .bundleName(existingBundle.getName())
                .imgUrl(existingBundle.getImgUrl())
                .localPrice(existingBundle.getLocalPrice())
                .touristPrice(existingBundle.getTouristPrice())
                .isPublished(existingBundle.isPublished())
                .build();
    }

    @Override
    @Transactional
    public Bundle toggleBundleStatus(User user, UUID bundleId) {
        // Step 1: Ensure the user is an admin
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        // Step 2: Find existing bundle
        Bundle existingBundle = bundleRepository.findById(bundleId)
                .orElseThrow(() -> new RuntimeException("Bundle not found with id: " + bundleId));

        // Step 3: Toggle the isPublished status
        existingBundle.setPublished(!existingBundle.isPublished());

        // Step 4: Save and return updated bundle
        return bundleRepository.save(existingBundle);
    }

    @Override
    public GetBundlesPaginationResponse getBundlesByPage(int amountPerPage, int page, String searchQuery) {
        validationService.validatePagination(page, amountPerPage);

        Pageable pageable = PageRequest.of(Math.max(0, page), amountPerPage);

        Page<Bundle> bundlePage;
        if (searchQuery != null && !searchQuery.isBlank()) {
            bundlePage = bundleRepository.findByNameContainingIgnoreCaseAndIsPublishedTrue(searchQuery.trim(), pageable);
        } else {
            bundlePage = bundleRepository.findByIsPublishedTrue(pageable);
        }

        List<BundleSummaryDTO> bundleResponses = bundlePage.getContent().stream()
                .map(bundle -> {
                    int totalDuration = bundleDetailRepository.findByBundle(bundle).stream()
                            .mapToInt(detail -> {
                                int duration = detail.getServicePrice().getDuration();
                                validationService.validateDuration(duration);
                                return duration * detail.getQuantity();
                            })
                            .sum();

                    return BundleSummaryDTO.builder()
                            .bundleName(bundle.getName())
                            .totalDuration(totalDuration)
                            .imgUrl(bundle.getImgUrl())
                            .build();
                })
                .collect(Collectors.toList());

        return GetBundlesPaginationResponse.builder()
                .bundles(bundleResponses)
                .totalPages(bundlePage.getTotalPages())
                .totalElements(bundlePage.getTotalElements())
                .build();
    }

    @Override
    public BundleDetailDTO getBundleDetails(UUID bundleId, String lang) {
        // Step 1: Retrieve the bundle entity by its ID
        Bundle bundle = bundleRepository.findById(bundleId)
                .orElseThrow(() -> new RuntimeException("Bundle not found with id: " + bundleId));

        // Step 2: Calculate the total duration using the helper method
        int totalDuration = getTotalDuration(bundle);

        // Step 3: Get the correct language code (assuming you map 'lang' to an enum, e.g., "en" -> LanguageCode.EN)
        LanguageCode languageCode = LanguageCode.valueOf(lang.toUpperCase());

        // Step 4: Use the repository method to find the description in the specified language
        BundleDescription bundleDescription = bundleDescriptionRepository
                .findByBundleAndLanguageCode(bundle, languageCode)
                .orElseThrow(() -> new RuntimeException("Description not found for the bundle in language: " + lang));

        // Step 5: Prepare and return the BundleDetailDTO with the required information
        return BundleDetailDTO.builder()
                .description(bundleDescription.getDescription())
                .includedItemDescription(bundleDescription.getIncludedItemDescription())
                .duration(totalDuration)  // Use the calculated total duration here
                .localPrice(bundle.getLocalPrice())
                .touristPrice(bundle.getTouristPrice())
                .build();
    }

    // Helper function
    @Override
    public int getTotalDuration(Bundle bundle) {
        // Fetch all bundle details for this specific bundle
        List<BundleDetail> bundleDetails = bundleDetailRepository.findByBundle(bundle);

        // Calculate total duration by summing up durations of services
        int totalDuration = 0;

        for (BundleDetail detail : bundleDetails) {
            ServicePrice servicePrice = detail.getServicePrice();
            int duration = servicePrice.getDuration(); // Get the duration of the service
            validationService.validateDuration(duration);
            totalDuration += duration;
        }

        return totalDuration; // Return the total duration for the bundle
    }

}
