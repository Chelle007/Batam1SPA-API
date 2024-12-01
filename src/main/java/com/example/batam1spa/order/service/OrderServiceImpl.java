package com.example.batam1spa.order.service;

import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.customer.repository.CustomerRepository;
import com.example.batam1spa.order.model.Order;
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
    private final CustomerRepository customerRepository;

    @Override
    public void seedOrder() {
        Customer customer1 = customerRepository.findByPhoneNumber("12345678").orElse(null);
        Customer customer2 = customerRepository.findByPhoneNumber("87654321").orElse(null);

        createOrderIfNotExists(customer1, true, 900000, LocalDateTime.now(), OrderStatus.BOOKED);
        createOrderIfNotExists(customer2, false, 500000, LocalDateTime.now(), OrderStatus.CANCELLED);
    }

    private void createOrderIfNotExists(Customer customer, boolean isVIP, int totalPrice, LocalDateTime bookDateTime, OrderStatus status) {
        boolean orderExists = orderRepository.existsByCustomer(customer);

        if (orderExists) {
            log.info("{}'s order already exists in the system", customer);
            return;
        }

        Order order = Order.builder()
                .customer(customer)
                .isVIP(isVIP)
                .totalPrice(totalPrice)
                .bookDateTime(bookDateTime)
                .status(status)
                .build();

        orderRepository.save(order);
        log.info("{}'s order has been added to the system", customer);
    }
}
