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
        createBundleIfNotExists("Head & Body - 2h", "headAndBody.png", 750000, 800000);
        createBundleIfNotExists("Foot & Body - 2h", "footAndBody.png", 950000, 1000000);
    }

    private void createBundleIfNotExists(String name, String imgUrl, int localPrice, int touristPrice) {
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
                .build();

        bundleRepository.save(bundle);
        log.info("{} has been added to the system", name);
    }
}
