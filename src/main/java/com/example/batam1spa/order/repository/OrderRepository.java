package com.example.batam1spa.order.repository;

import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.customer.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    // for testing purpose (seeder)
    Boolean existsByCustomer(Customer customer);
    Order findByCustomer(Customer customer);
}
