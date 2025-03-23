package com.example.batam1spa.bundle.service;

import com.example.batam1spa.bundle.dto.BundleDTO;
import com.example.batam1spa.bundle.dto.CreateBundleDTO;
import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.bundle.model.BundleDetail;
import com.example.batam1spa.bundle.repository.BundleDetailRepository;
import com.example.batam1spa.bundle.repository.BundleRepository;
import com.example.batam1spa.service.repository.ServicePriceRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.service.model.ServicePrice;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BundleServiceImpl implements BundleService {
    private final RoleSecurityService roleSecurityService;
    private final BundleRepository bundleRepository;
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
}
