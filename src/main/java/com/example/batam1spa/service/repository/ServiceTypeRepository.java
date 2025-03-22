package com.example.batam1spa.service.repository;

import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceTypeRepository extends JpaRepository<Service, UUID> {
    // Custom query methods can be added here
    // Example: Find services by service type
    List<Service> findByServiceType(ServiceType serviceType);
}