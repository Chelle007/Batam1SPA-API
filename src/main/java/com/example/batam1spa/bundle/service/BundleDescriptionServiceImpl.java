package com.example.batam1spa.bundle.service;

import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.bundle.model.BundleDescription;
import com.example.batam1spa.bundle.repository.BundleDescriptionRepository;
import com.example.batam1spa.bundle.repository.BundleRepository;
import com.example.batam1spa.common.model.LanguageCode;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BundleDescriptionServiceImpl implements BundleDescriptionService {
    private final RoleSecurityService roleSecurityService;
    private final BundleDescriptionRepository bundleDescriptionRepository;
    private final BundleRepository bundleRepository;

    @Override
    public void seedBundleDescription() {
        Bundle bundle1 = bundleRepository.findByName("Head & Body - 2h").orElse(null);
        Bundle bundle2 = bundleRepository.findByName("Foot & Body - 2h").orElse(null);

        createBundleDescriptionIfNotExists(
                bundle1,
                LanguageCode.EN,
                "Bundle for head massage 1h + body massage 1h",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );

        createBundleDescriptionIfNotExists(
                bundle1,
                LanguageCode.ID,
                "Paket massage kepala 1 jam + massage badan 1 jam",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );

        createBundleDescriptionIfNotExists(
                bundle2,
                LanguageCode.EN,
                "Bundle for foot massage 1h + body massage 1h",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );

        createBundleDescriptionIfNotExists(
                bundle2,
                LanguageCode.ID,
                "Paket massage kaki 1 jam + massage badan 1 jam",
                "Use of heated stones to ease muscle stiffness and tension\n" +
                        "Professional massage techniques to improve circulation and reduce stress\n" +
                        "A calming and tranquil atmosphere for enhanced relaxation and well-being"
        );
    }

    private void createBundleDescriptionIfNotExists(Bundle bundle, LanguageCode languageCode, String description, String includedItemDescription) {
        boolean bundleDescriptionExists = bundleDescriptionRepository.existsByBundleAndLanguageCode(bundle, languageCode);

        if (bundleDescriptionExists) {
            log.info("{}'s {} description already exists in the system", bundle, languageCode);
            return;
        }

        BundleDescription bundleDescription = BundleDescription.builder()
                .bundle(bundle)
                .languageCode(languageCode)
                .description(description)
                .includedItemDescription(includedItemDescription)
                .build();

        bundleDescriptionRepository.save(bundleDescription);
        log.info("{} has been added to the system", bundleDescription);
    }

    public List<BundleDescription> getAllBundleDescriptions(User user) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
        return bundleDescriptionRepository.findAll();
    }
}
