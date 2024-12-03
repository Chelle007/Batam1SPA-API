package com.example.batam1spa.bundle.repository;

import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.bundle.model.BundleDetail;
import com.example.batam1spa.service.model.ServicePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BundleDetailRepository extends JpaRepository<BundleDetail, UUID> {
    // for testing purpose (seeder)
    Boolean existsByBundleAndServicePrice(Bundle bundle, ServicePrice servicePrice);
}
