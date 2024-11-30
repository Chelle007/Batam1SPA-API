package com.example.batam1spa.order.service;

import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.order.model.OrderStatus;
import com.example.batam1spa.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Override
    public void seedOrder() {

    }

    private void createOrderIfNotExists(Customer customer, boolean isVIP, int actualPrice, int discountedPrice, int totalPrice, LocalDateTime bookDateTime, OrderStatus status) {
        boolean orderExists = orderRepository.existsByCustomer(customer);
    }
}
