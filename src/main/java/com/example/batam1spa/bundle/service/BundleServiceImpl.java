package com.example.batam1spa.bundle.service;

import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.bundle.repository.BundleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BundleServiceImpl implements BundleService {
    private final BundleRepository bundleRepository;

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
}
