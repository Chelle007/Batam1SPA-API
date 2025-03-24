package com.example.batam1spa.service.repository;

import com.example.batam1spa.common.model.LanguageCode;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.model.ServiceDescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ServiceDescriptionRepository extends JpaRepository<ServiceDescription, UUID> {
    // for testing purpose (seeder)
    Boolean existsByServiceAndLanguageCode(Service service, LanguageCode languageCode);
    void deleteByServiceId(UUID serviceId);
    Optional<ServiceDescription> findByServiceAndLanguageCode(Service service, LanguageCode languageCode);
}
