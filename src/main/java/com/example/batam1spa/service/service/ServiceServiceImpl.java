package com.example.batam1spa.service.service;

import com.example.batam1spa.common.model.LanguageCode;
import com.example.batam1spa.service.dto.*;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.model.ServiceDescription;
import com.example.batam1spa.service.model.ServicePrice;
import com.example.batam1spa.service.model.ServiceType;
import com.example.batam1spa.service.repository.ServiceDescriptionRepository;
import com.example.batam1spa.service.repository.ServicePriceRepository;
import com.example.batam1spa.service.repository.ServiceRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class ServiceServiceImpl implements ServiceService {
    private final RoleSecurityService roleSecurityService;
    private final ServiceRepository serviceRepository;
    private final ServicePriceRepository servicePriceRepository;
    private final ServiceDescriptionRepository serviceDescriptionRepository;
    private final ModelMapper modelMapper;

    @Override
    public void seedService() {
        createServiceIfNotExists("Head Massage", ServiceType.MASSAGE, "head_massage.png");
        createServiceIfNotExists("Body Massage", ServiceType.MASSAGE, "body_massage.png");
        createServiceIfNotExists("Foot Massage", ServiceType.MASSAGE, "foot_massage.png");
        createServiceIfNotExists("Manipedi", ServiceType.MANIPEDI, "manipedi.png");
    }

    private void createServiceIfNotExists(String name, ServiceType serviceType, String imgUrl) {
        boolean serviceExists = serviceRepository.existsByName(name);

        if (serviceExists) {
            log.info("{} already exists in the system", name);
            return;
        }

        Service service = Service.builder()
                .name(name)
                .serviceType(serviceType)
                .imgUrl(imgUrl)
                .build();

        serviceRepository.save(service);
        log.info("{} has been added to the system", name);
    }

    @Override
    public List<ServiceRequest> getAllService(User user) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
        List<Service> services = serviceRepository.findAll();

        return services.stream().flatMap(service -> {
            List<ServicePrice> prices = servicePriceRepository.findByService(service);

            return prices.stream().map(price -> ServiceRequest.builder()
                    .serviceId(service.getId())
                    .name(service.getName())
                    .duration(price.getDuration())
                    .imgUrl(service.getImgUrl())
                    .localPrice(price.getLocalPrice())
                    .touristPrice(price.getTouristPrice())
                    .isPublished(service.isPublished())
                    .build());
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Service addService(User user, CreateServiceRequest createServiceRequest) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
        // Check if the service already exists by name
        if (serviceRepository.existsByName(createServiceRequest.getName())) {
            throw new RuntimeException("Service with this name already exists!");
        }

        // Create and save the service entity
        final Service savedService = serviceRepository.save(Service.builder()
                .name(createServiceRequest.getName())
                .imgUrl(createServiceRequest.getImgUrl())
                .isPublished(createServiceRequest.getIsPublished())
                .build());

        // Save Service Prices
        if (createServiceRequest.getPrices() != null && !createServiceRequest.getPrices().isEmpty()) {
            List<ServicePrice> servicePrices = createServiceRequest.getPrices().stream()
                    .map(price -> ServicePrice.builder()
                            .service(savedService)
                            .duration(price.getDuration())
                            .localPrice(price.getLocalPrice())
                            .touristPrice(price.getTouristPrice())
                            .build())
                    .collect(Collectors.toList());

            servicePriceRepository.saveAll(servicePrices);
        }

        // Save Service Descriptions (EN and ID)
        List<ServiceDescription> descriptions = new ArrayList<>();

        if (createServiceRequest.getENDescription() != null) {
            descriptions.add(ServiceDescription.builder()
                    .service(savedService)
                    .languageCode(LanguageCode.EN)
                    .description(createServiceRequest.getENDescription())
                    .build());
        }

        if (createServiceRequest.getIDDescription() != null) {
            descriptions.add(ServiceDescription.builder()
                    .service(savedService)
                    .languageCode(LanguageCode.ID)
                    .description(createServiceRequest.getIDDescription())
                    .build());
        }

        // Save Included Item Descriptions (EN and ID)
        if (createServiceRequest.getENIncludedItemDescription() != null) {
            descriptions.add(ServiceDescription.builder()
                    .service(savedService)
                    .languageCode(LanguageCode.EN)
                    .description(createServiceRequest.getENIncludedItemDescription())
                    .build());
        }

        if (createServiceRequest.getIDIncludedItemDescription() != null) {
            descriptions.add(ServiceDescription.builder()
                    .service(savedService)
                    .languageCode(LanguageCode.ID)
                    .description(createServiceRequest.getIDIncludedItemDescription())
                    .build());
        }

        if (!descriptions.isEmpty()) {
            serviceDescriptionRepository.saveAll(descriptions);
        }

        return savedService;
    }

/*
Expected API Request for add service:
{
    "name": "Full Body Massage",
    "imgUrl": "full_body_massage.png",
    "prices": [
        { "duration": 30, "localPrice": 100000, "touristPrice": 150000 },
        { "duration": 60, "localPrice": 200000, "touristPrice": 250000 },
        { "duration": 90, "localPrice": 300000, "touristPrice": 350000 }
    ],
    "IDIncludedItemDescription": "Handuk yang lembut",
    "ENIncludedItemDescription": "Soft towel",
    "IDDescription": "Pijat seluruh tubuh yang menenangkan.",
    "ENDescription": "A relaxing full body massage.",
    "isPublished": true
}
 */

    @Override
    @Transactional
    public Service editService(User user, UUID serviceId, EditServiceRequest editServiceRequest) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
        // Step 1: Find existing service
        Service existingService = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));

        // Step 2: Update `imgUrl` and `isPublished`
        if (editServiceRequest.getImgUrl() != null) {
            existingService.setImgUrl(editServiceRequest.getImgUrl());
        }
        if (editServiceRequest.getIsPublished() != null) {
            existingService.setPublished(editServiceRequest.getIsPublished());
        }

        // Step 3: Update Service Prices
        if (editServiceRequest.getPrices() != null && !editServiceRequest.getPrices().isEmpty()) {
            // Remove existing prices
            servicePriceRepository.deleteByServiceId(serviceId);

            // Save new prices
            List<ServicePrice> newPrices = editServiceRequest.getPrices().stream()
                    .map(price -> ServicePrice.builder()
                            .service(existingService)
                            .duration(price.getDuration())
                            .localPrice(price.getLocalPrice())
                            .touristPrice(price.getTouristPrice())
                            .build())
                    .collect(Collectors.toList());
            servicePriceRepository.saveAll(newPrices);
        }

        // Step 4: Update Service Descriptions (EN and ID)
        List<ServiceDescription> descriptions = new ArrayList<>();

        // Remove old descriptions
        serviceDescriptionRepository.deleteByServiceId(serviceId);

        if (editServiceRequest.getENDescription() != null) {
            descriptions.add(ServiceDescription.builder()
                    .service(existingService)
                    .languageCode(LanguageCode.EN)
                    .description(editServiceRequest.getENDescription())
                    .build());
        }

        if (editServiceRequest.getIDDescription() != null) {
            descriptions.add(ServiceDescription.builder()
                    .service(existingService)
                    .languageCode(LanguageCode.ID)
                    .description(editServiceRequest.getIDDescription())
                    .build());
        }

        // Step 5: Update Included Item Descriptions (EN and ID)
        if (editServiceRequest.getENIncludedItemDescription() != null) {
            descriptions.add(ServiceDescription.builder()
                    .service(existingService)
                    .languageCode(LanguageCode.EN)
                    .description(editServiceRequest.getENIncludedItemDescription())
                    .build());
        }

        if (editServiceRequest.getIDIncludedItemDescription() != null) {
            descriptions.add(ServiceDescription.builder()
                    .service(existingService)
                    .languageCode(LanguageCode.ID)
                    .description(editServiceRequest.getIDIncludedItemDescription())
                    .build());
        }

        if (!descriptions.isEmpty()) {
            serviceDescriptionRepository.saveAll(descriptions);
        }

        return serviceRepository.save(existingService);
    }

    @Override
    @Transactional
    public Service toggleServiceStatus(User user, UUID serviceId) {
        // Step 1: Ensure the user is an admin
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        // Step 2: Find existing service
        Service existingService = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));

        // Step 3: Toggle the isPublished status
        existingService.setPublished(!existingService.isPublished());

        // Step 4: Save and return updated service
        return serviceRepository.save(existingService);
    }

    // Customer side
    @Override
    public ServiceDetailsDTO getServiceDetails(UUID serviceId, String lang) {
        // Step 1: Find service
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found with id: " + serviceId));

        // Step 2: Fetch service description in requested language
        ServiceDescription description = serviceDescriptionRepository
                .findByServiceAndLanguageCode(service, LanguageCode.valueOf(lang.toUpperCase()))
                .orElseThrow(() -> new RuntimeException("Service description not found for language: " + lang));

        // Step 3: Get included item descriptions in requested language
        String includedItems = serviceDescriptionRepository
                .findByServiceAndLanguageCode(service, LanguageCode.valueOf(lang.toUpperCase()))
                .map(ServiceDescription::getDescription)
                .orElse(lang.equalsIgnoreCase("EN") ? "No included items available." : "Tidak ada item yang termasuk.");

        // Step 4: Fetch pricing (60/90/120 mins)
        List<PriceDTO> prices = servicePriceRepository.findByService(service).stream()
                .filter(price -> price.getDuration() == 60 || price.getDuration() == 90 || price.getDuration() == 120)
                .map(price -> PriceDTO.builder()
                        .duration(price.getDuration())
                        .localPrice(price.getLocalPrice())
                        .touristPrice(price.getTouristPrice())
                        .build())
                .collect(Collectors.toList());

        // Step 5: Build response DTO
        return ServiceDetailsDTO.builder()
                .serviceId(service.getId().toString())
                .description(description.getDescription())
                .includedItems(includedItems)
                .prices(prices)
                .build();
    }

    @Override
    public GetServicesPaginationResponse getServicesByPage(int amountPerPage, int page, String searchQuery) {
        // Create a Pageable object (ensure page is non-negative)
        Pageable pageable = PageRequest.of(Math.max(0, page), amountPerPage);

        // Fetch services with optional search filter
        Page<Service> servicePage;
        if (searchQuery != null && !searchQuery.isBlank()) {
            servicePage = serviceRepository.findByNameContainingIgnoreCase(searchQuery.trim(), pageable);
        } else {
            servicePage = serviceRepository.findAll(pageable);
        }

        // Convert the Page of services to a List of ServicePaginationResponse DTOs
        List<ServicePaginationResponse> serviceResponses = servicePage.getContent().stream()
                .map(service -> {
                    // Get the durations for each service
                    List<ServicePrice> prices = servicePriceRepository.findByService(service);

                    int shortestDuration = prices.isEmpty() ? 0 : prices.stream().mapToInt(ServicePrice::getDuration).min().orElse(0);
                    int longestDuration = prices.isEmpty() ? 0 : prices.stream().mapToInt(ServicePrice::getDuration).max().orElse(0);

                    return ServicePaginationResponse.builder()
                            .serviceId(service.getId())
                            .name(service.getName())
                            .shortestDuration(shortestDuration)
                            .longestDuration(longestDuration)
                            .imgUrl(service.getImgUrl())
                            .isPublished(service.isPublished())
                            .build();
                })
                .collect(Collectors.toList());

        // Return a GetServicesPaginationResponse that contains the services and pagination metadata
        return GetServicesPaginationResponse.builder()
                .services(serviceResponses)
                .totalPages(servicePage.getTotalPages())
                .totalElements(servicePage.getTotalElements())
                .build();
    }

    @Override
    public List<ServicePaginationResponse> getSignatureService() {
        Service service1 = serviceRepository.findByName("Head Massage").orElseThrow(/*TODO: () -> new exception*/);
        Service service2 = serviceRepository.findByName("Body Massage").orElseThrow(/*TODO: () -> new exception*/);
        Service service3 = serviceRepository.findByName("Foot Massage").orElseThrow(/*TODO: () -> new exception*/);

        ServicePaginationResponse servicePaginationResponse1 = modelMapper.map(service1, ServicePaginationResponse.class);
        List<ServicePrice> prices = servicePriceRepository.findByService(service1);
        int shortestDuration = prices.isEmpty() ? 0 : prices.stream().mapToInt(ServicePrice::getDuration).min().orElse(0);
        int longestDuration = prices.isEmpty() ? 0 : prices.stream().mapToInt(ServicePrice::getDuration).max().orElse(0);
        servicePaginationResponse1.setShortestDuration(shortestDuration);
        servicePaginationResponse1.setLongestDuration(longestDuration);

        ServicePaginationResponse servicePaginationResponse2 = modelMapper.map(service2, ServicePaginationResponse.class);
        prices = servicePriceRepository.findByService(service2);
        shortestDuration = prices.isEmpty() ? 0 : prices.stream().mapToInt(ServicePrice::getDuration).min().orElse(0);
        longestDuration = prices.isEmpty() ? 0 : prices.stream().mapToInt(ServicePrice::getDuration).max().orElse(0);
        servicePaginationResponse1.setShortestDuration(shortestDuration);
        servicePaginationResponse1.setLongestDuration(longestDuration);

        ServicePaginationResponse servicePaginationResponse3 = modelMapper.map(service3, ServicePaginationResponse.class);
        prices = servicePriceRepository.findByService(service2);
        shortestDuration = prices.isEmpty() ? 0 : prices.stream().mapToInt(ServicePrice::getDuration).min().orElse(0);
        longestDuration = prices.isEmpty() ? 0 : prices.stream().mapToInt(ServicePrice::getDuration).max().orElse(0);
        servicePaginationResponse1.setShortestDuration(shortestDuration);
        servicePaginationResponse1.setLongestDuration(longestDuration);

        return List.of(servicePaginationResponse1, servicePaginationResponse2, servicePaginationResponse3);
    }
}