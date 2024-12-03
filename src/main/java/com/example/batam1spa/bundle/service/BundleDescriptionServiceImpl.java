package com.example.batam1spa.bundle.service;

import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.bundle.model.BundleDescription;
import com.example.batam1spa.bundle.repository.BundleDescriptionRepository;
import com.example.batam1spa.bundle.repository.BundleRepository;
import com.example.batam1spa.common.model.LanguageCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BundleDescriptionServiceImpl implements BundleDescriptionService {
    private final BundleDescriptionRepository bundleDescriptionRepository;
    private final BundleRepository bundleRepository;

    @Override
    public void seedBundleDescription() {
        Bundle bundle1 = bundleRepository.findByName("Head & Body - 2h").orElse(null);
        Bundle bundle2 = bundleRepository.findByName("Foot & Body - 2h").orElse(null);

        createBundleDescriptionIfNotExists(
                bundle1,
                LanguageCode.EN,
                "Bundle for head massage 1h + body massage 1h"
        );

        createBundleDescriptionIfNotExists(
                bundle1,
                LanguageCode.ID,
                "Paket massage kepala 1 jam + massage badan 1 jam"
        );

        createBundleDescriptionIfNotExists(
                bundle2,
                LanguageCode.EN,
                "Bundle for foot massage 1h + body massage 1h"
        );

        createBundleDescriptionIfNotExists(
                bundle2,
                LanguageCode.ID,
                "Paket massage kaki 1 jam + massage badan 1 jam"
        );
    }

    private void createBundleDescriptionIfNotExists(Bundle bundle, LanguageCode languageCode, String description) {
        boolean bundleDescriptionExists = bundleDescriptionRepository.existsByBundleAndLanguageCode(bundle, languageCode);

        if (bundleDescriptionExists) {
            log.info("{}'s {} description already exists in the system", bundle, languageCode);
            return;
        }

        BundleDescription bundleDescription = BundleDescription.builder()
                .bundle(bundle)
                .languageCode(languageCode)
                .description(description)
                .build();

        bundleDescriptionRepository.save(bundleDescription);
        log.info("{} has been added to the system", bundleDescription);
    }
}
