package com.example.batam1spa.service.service;

import com.example.batam1spa.common.model.LanguageCode;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.service.model.ServiceDescription;
import com.example.batam1spa.service.repository.ServiceDescriptionRepository;
import com.example.batam1spa.service.repository.ServiceRepository;
import com.example.batam1spa.user.model.User;
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
                "Very relaxing massage for head.",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );
        createServiceDescriptionIfNotExists(
                service1,
                LanguageCode.ID,
                "Massage untuk kepala.",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );
        createServiceDescriptionIfNotExists(
                service2,
                LanguageCode.EN,
                "Very relaxing massage for body.",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );
        createServiceDescriptionIfNotExists(
                service2,
                LanguageCode.ID,
                "Massage untuk badan.",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );
        createServiceDescriptionIfNotExists(
                service3,
                LanguageCode.EN,
                "Very relaxing massage for foot.",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );
        createServiceDescriptionIfNotExists(
                service3,
                LanguageCode.ID,
                "Massage untuk kaki.",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );
        createServiceDescriptionIfNotExists(
                service4,
                LanguageCode.EN,
                "Manicure & Pedicure.",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );
        createServiceDescriptionIfNotExists(
                service4,
                LanguageCode.ID,
                "Servis manipedi.",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );
    }

    private void createServiceDescriptionIfNotExists(Service service, LanguageCode languageCode, String description, String includedItemDescription) {
        boolean serviceDescriptionExists = serviceDescriptionRepository.existsByServiceAndLanguageCode(service, languageCode);

        if (serviceDescriptionExists) {
            log.info("{}'s {} description already exists in the system", service, languageCode);
            return;
        }

        ServiceDescription serviceDescription = ServiceDescription.builder()
                .service(service)
                .languageCode(languageCode)
                .description(description)
                .includedItemDescription(includedItemDescription)
                .build();

        serviceDescriptionRepository.save(serviceDescription);
        log.info("{}'s {} description has been added to the system", service, languageCode);
    }

    public List<ServiceDescription> getAllServiceDescriptions(User user) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
        return serviceDescriptionRepository.findAll();
    }

}
