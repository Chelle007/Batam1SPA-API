package com.example.batam1spa.bundle.service;

import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.bundle.model.BundleDetail;
import com.example.batam1spa.bundle.repository.BundleDetailRepository;
import com.example.batam1spa.bundle.repository.BundleRepository;
import com.example.batam1spa.service.model.ServicePrice;
import com.example.batam1spa.service.repository.ServicePriceRepository;
import com.example.batam1spa.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.batam1spa.service.model.Service;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class BundleDetailServiceImpl implements BundleDetailService {
    private final BundleDetailRepository bundleDetailRepository;
    private final BundleRepository bundleRepository;
    private final ServiceRepository serviceRepository;
    private final ServicePriceRepository servicePriceRepository;

    @Override
    public void seedBundleDetail() {
        Bundle bundle1 = bundleRepository.findByName("Head & Body - 2h").orElse(null);
        Bundle bundle2 = bundleRepository.findByName("Foot & Body - 2h").orElse(null);
        Service service1 = serviceRepository.findByName("Head Massage").orElse(null);
        Service service2 = serviceRepository.findByName("Body Massage").orElse(null);
        Service service3 = serviceRepository.findByName("Foot Massage").orElse(null);
        ServicePrice servicePrice1 = servicePriceRepository.findByServiceAndDuration(service1, 60).orElse(null);
        ServicePrice servicePrice2 = servicePriceRepository.findByServiceAndDuration(service2, 60).orElse(null);
        ServicePrice servicePrice3 = servicePriceRepository.findByServiceAndDuration(service3, 60).orElse(null);

        createBundleDetailIfNotExists(bundle1, servicePrice1, 1);
        createBundleDetailIfNotExists(bundle1, servicePrice2, 1);
        createBundleDetailIfNotExists(bundle2, servicePrice2, 1);
        createBundleDetailIfNotExists(bundle2, servicePrice3, 1);

    }

    private void createBundleDetailIfNotExists(Bundle bundle, ServicePrice servicePrice, int quantity) {
        boolean bundleDetailExists = bundleDetailRepository.existsByBundleAndServicePrice(bundle, servicePrice);

        if (bundleDetailExists) {
            log.info("{} bundle detail about {} already exists in the system", bundle, servicePrice);
            return;
        }

        BundleDetail bundleDetail = BundleDetail.builder()
                .bundle(bundle)
                .servicePrice(servicePrice)
                .quantity(quantity)
                .build();

        bundleDetailRepository.save(bundleDetail);
        log.info("{} has been added to the system", bundleDetail);
    }
}
