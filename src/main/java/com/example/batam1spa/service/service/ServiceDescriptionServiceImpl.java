package com.example.batam1spa.service.service;

import com.example.batam1spa.common.model.LanguageCode;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.service.model.ServiceDescription;
import com.example.batam1spa.service.repository.ServiceDescriptionRepository;
import com.example.batam1spa.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.batam1spa.service.model.Service;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class ServiceDescriptionServiceImpl implements ServiceDescriptionService {
    private final RoleSecurityService roleSecurityService;
    private final ServiceDescriptionRepository serviceDescriptionRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public void seedServiceDescription() {
        Service service1 = serviceRepository.findByName("Head Massage").orElse(null);
        Service service2 = serviceRepository.findByName("Body Massage").orElse(null);
        Service service3 = serviceRepository.findByName("Foot Massage").orElse(null);
        Service service4 = serviceRepository.findByName("Manipedi").orElse(null);

        createServiceDescriptionIfNotExists(
                service1,
                LanguageCode.EN,
                "Very relaxing massage for head."
        );
        createServiceDescriptionIfNotExists(
                service1,
                LanguageCode.ID,
                "Massage untuk kepala."
        );
        createServiceDescriptionIfNotExists(
                service2,
                LanguageCode.EN,
                "Very relaxing massage for body."
        );
        createServiceDescriptionIfNotExists(
                service2,
                LanguageCode.ID,
                "Massage untuk badan."
        );
        createServiceDescriptionIfNotExists(
                service3,
                LanguageCode.EN,
                "Very relaxing massage for foot."
        );
        createServiceDescriptionIfNotExists(
                service3,
                LanguageCode.ID,
                "Massage untuk kaki."
        );
        createServiceDescriptionIfNotExists(
                service4,
                LanguageCode.EN,
                "Manicure & Pedicure."
        );
        createServiceDescriptionIfNotExists(
                service4,
                LanguageCode.ID,
                "Servis manipedi."
        );
    }

    private void createServiceDescriptionIfNotExists(Service service, LanguageCode languageCode, String description) {
        boolean serviceDescriptionExists = serviceDescriptionRepository.existsByServiceAndLanguageCode(service, languageCode);

        if (serviceDescriptionExists) {
            log.info("{}'s {} description already exists in the system", service, languageCode);
            return;
        }

        ServiceDescription serviceDescription = ServiceDescription.builder()
                .service(service)
                .languageCode(languageCode)
                .description(description)
                .build();

        serviceDescriptionRepository.save(serviceDescription);
        log.info("{}'s {} description has been added to the system", service, languageCode);
    }

    public List<ServiceDescription> getAllServiceDescriptions() {
        return serviceDescriptionRepository.findAll();
    }

}
