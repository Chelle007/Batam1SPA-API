package com.example.batam1spa.staff.repository;

import com.example.batam1spa.staff.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StaffRepository extends JpaRepository<Staff, UUID> {
    // for testing purpose (seeder)
    Boolean existsByFullName(String fullName);
    Optional<Staff> findByFullName(String fullName);
}
