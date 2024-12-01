package com.example.batam1spa.service.service;

import com.example.batam1spa.service.model.Service;
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
        createServiceIfNotExists("Head Massage", "head_massage.png");
        createServiceIfNotExists("Body Massage", "body_massage.png");
        createServiceIfNotExists("Foot Massage", "foot_massage.png");
    }

    private void createServiceIfNotExists(String name, String imgUrl) {
        boolean serviceExists = serviceRepository.existsByName(name);

        if (serviceExists) {
            log.info("{} already exists in the system", name);
            return;
        }

        Service service = Service.builder()
                .name(name)
                .imgUrl(imgUrl)
                .build();

        serviceRepository.save(service);
        log.info("{} has been added to the system", name);
    }
}
