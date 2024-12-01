package com.example.batam1spa.service.service;

import com.example.batam1spa.service.repository.ServicePriceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicePriceServiceImpl implements ServicePriceService {
    private final ServicePriceRepository servicePriceRepository;
    private final ServiceService serviceService;

    @Override
    public void seedServicePrice() {

    }
}
