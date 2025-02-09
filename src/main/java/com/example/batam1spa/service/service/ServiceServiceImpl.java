package com.example.batam1spa.service.service;

import com.example.batam1spa.service.dto.ServiceRequest;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.model.ServicePrice;
import com.example.batam1spa.service.model.ServiceType;
import com.example.batam1spa.service.repository.ServicePriceRepository;
import com.example.batam1spa.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;
    private final ServicePriceRepository servicePriceRepository;
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
                    .isActive(service.isActive())
                    .build());
        }).collect(Collectors.toList());
    }


    // Get all services in a list of DTO
//    @Override
//    public List<ServiceRequest> getAllService() {
//        // Fetch all services from the repository
//        List<Service> services = serviceRepository.findAll();
//
//        // Convert list of class to list of DTO
//        // Map Service entities to ServiceRequest objects
//        return services.stream()
//                .map(service -> modelMapper.map(service, ServiceRequest.class))
//                .collect(Collectors.toList());
//    }

    // Get all services in a list of DTO
//    @Override
//    public Service addService(CreateServiceRequest createServiceRequest) {
//        // DTO entity conversion
//        Service createServiceEntity = modelMapper.map(createServiceRequest, Service.class);
//        return staffRepository.save(createServiceEntity);
//    }

}
