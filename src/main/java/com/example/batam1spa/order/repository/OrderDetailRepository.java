package com.example.batam1spa.order.repository;

import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.order.model.OrderDetail;
import com.example.batam1spa.service.model.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    Boolean existsByOrderAndService(Order order, Service service);
    Page<OrderDetail> findByServiceDate(LocalDate serviceDate, Pageable pageable);
    List<OrderDetail> findByOrder(Order order);
}
