package com.example.batam1spa.order.repository;

import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.order.model.OrderDetail;
import com.example.batam1spa.service.model.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    // for testing purpose (seeder)
    Boolean existsByOrderAndService(Order order, Service service);

    List<OrderDetail> findByServiceDate(LocalDate serviceDate);
}
