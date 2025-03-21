package com.example.batam1spa.service.repository;

import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.model.ServicePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface ServicePriceRepository extends JpaRepository<ServicePrice, UUID> {
    // for testing purpose (seeder)
    Boolean existsByServiceAndDuration(Service service, int duration);
    Optional<ServicePrice> findByServiceAndDuration(Service service, int duration);
    List<ServicePrice> findByService(Service service);

    void deleteByServiceId(UUID serviceId);
}
