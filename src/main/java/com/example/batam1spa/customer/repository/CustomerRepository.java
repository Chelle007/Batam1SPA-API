package com.example.batam1spa.customer.repository;

import com.example.batam1spa.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    Boolean existsByPhoneNumber(String phoneNumber);
}