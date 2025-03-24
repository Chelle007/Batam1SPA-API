package com.example.batam1spa.bundle.repository;

import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.bundle.model.BundleDescription;
import com.example.batam1spa.common.model.LanguageCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BundleDescriptionRepository extends JpaRepository<BundleDescription, UUID> {
    // for testing purpose (seeder)
    Boolean existsByBundleAndLanguageCode(Bundle bundle, LanguageCode languageCode);
    // Method to find the BundleDescription by Bundle and LanguageCode
    Optional<BundleDescription> findByBundleAndLanguageCode(Bundle bundle, LanguageCode languageCode);
}
