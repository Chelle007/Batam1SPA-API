package com.example.batam1spa.bundle.repository;

import com.example.batam1spa.bundle.model.Bundle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BundleRepository extends JpaRepository<Bundle, UUID> {
    // for testing purpose (seeder)
    Boolean existsByName(String name);
    Optional<Bundle> findByName(String name);
    Page<Bundle> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // Search published bundles by name (case-insensitive)
    Page<Bundle> findByNameContainingIgnoreCaseAndIsPublishedTrue(String name, Pageable pageable);

    // Get only published bundles (without search)
    Page<Bundle> findByIsPublishedTrue(Pageable pageable);
}