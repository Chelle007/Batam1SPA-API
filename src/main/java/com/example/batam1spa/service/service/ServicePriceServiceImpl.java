package com.example.batam1spa.service.service;

import com.example.batam1spa.service.model.ServicePrice;
import com.example.batam1spa.service.repository.ServicePriceRepository;
import com.example.batam1spa.service.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.example.batam1spa.service.model.Service;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class ServicePriceServiceImpl implements ServicePriceService {
    private final ServicePriceRepository servicePriceRepository;
    private final ServiceRepository serviceRepository;

    @Override
    public void seedServicePrice() {
        Service service1 = serviceRepository.findByName("Head Massage").orElse(null);
        Service service2 = serviceRepository.findByName("Foot Massage").orElse(null);
        Service service3 = serviceRepository.findByName("Body Massage").orElse(null);

        createServicePriceIfNotExists(service1, 60, 100000, 150000);
        createServicePriceIfNotExists(service1, 90, 200000, 250000);
        createServicePriceIfNotExists(service1, 120, 300000, 350000);
        createServicePriceIfNotExists(service2, 60, 400000, 450000);
        createServicePriceIfNotExists(service2, 90, 500000, 550000);
        createServicePriceIfNotExists(service2, 120, 600000, 650000);
        createServicePriceIfNotExists(service3, 60, 700000, 750000);
        createServicePriceIfNotExists(service3, 90, 800000, 850000);
        createServicePriceIfNotExists(service3, 120, 900000, 950000);
    }

    private void createServicePriceIfNotExists(Service service, int duration, int localPrice, int touristPrice) {
        boolean servicePriceExists = servicePriceRepository.existsByServiceAndDuration(service, duration);

        if (servicePriceExists) {
            log.info("{}'s {} mins price already exists in the system", service, duration);
            return;
        }

        ServicePrice servicePrice = ServicePrice.builder()
                .service(service)
                .duration(duration)
                .localPrice(localPrice)
                .touristPrice(touristPrice)
                .build();

        servicePriceRepository.save(servicePrice);
        log.info("{}'s {} mins price has been added to the system", service, duration);

    }
}
