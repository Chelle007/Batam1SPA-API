package com.example.batam1spa.service.repository;

import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.model.ServicePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServicePriceRepository extends JpaRepository<ServicePrice, UUID> {
    // for testing purpose (seeder)
    Boolean existsByServiceAndDuration(Service service, int duration);
}
