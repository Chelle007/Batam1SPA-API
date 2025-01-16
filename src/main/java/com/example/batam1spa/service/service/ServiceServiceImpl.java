package com.example.batam1spa.service.service;

import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.model.ServiceType;
import com.example.batam1spa.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class ServiceServiceImpl implements ServiceService {
    private final ServiceRepository serviceRepository;

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
}
