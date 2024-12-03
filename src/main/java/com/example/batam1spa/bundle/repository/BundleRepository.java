package com.example.batam1spa.bundle.repository;

import com.example.batam1spa.bundle.model.Bundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BundleRepository extends JpaRepository<Bundle, UUID> {
    // for testing purpose (seeder)
    Boolean existsByName(String name);
}
