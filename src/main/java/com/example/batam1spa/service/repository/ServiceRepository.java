package com.example.batam1spa.service.repository;

import com.example.batam1spa.service.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceRepository extends JpaRepository<Service, UUID> {
    // for testing purpose (seeder)
    Boolean existsByName(String name);
}