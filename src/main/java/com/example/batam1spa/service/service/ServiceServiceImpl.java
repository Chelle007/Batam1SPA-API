package com.example.batam1spa.service.service;

import com.example.batam1spa.common.model.LanguageCode;
import com.example.batam1spa.service.dto.CreateServiceRequest;
import com.example.batam1spa.service.dto.EditServiceRequest;
import com.example.batam1spa.service.dto.ServiceRequest;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.model.ServiceDescription;
import com.example.batam1spa.service.model.ServicePrice;
import com.example.batam1spa.service.model.ServiceType;
import com.example.batam1spa.service.repository.ServiceDescriptionRepository;
import com.example.batam1spa.service.repository.ServicePriceRepository;
import com.example.batam1spa.service.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class ServiceServiceImpl implements ServiceService {
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

    public List<ServiceRequest> getAllService() {
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
    public Service addService(CreateServiceRequest createServiceRequest) {
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
    public Service editService(UUID serviceId, EditServiceRequest editServiceRequest) {
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
}