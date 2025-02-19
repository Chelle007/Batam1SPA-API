package com.example.batam1spa.order.service;

import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.customer.repository.CustomerRepository;
import com.example.batam1spa.order.exception.OrderExceptions;
import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.order.repository.OrderRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RoleSecurityService roleSecurityService;

    @Override
    public void seedOrder() {
        Customer customer1 = customerRepository.findByPhoneNumber("12345678").orElse(null);
        Customer customer2 = customerRepository.findByPhoneNumber("87654321").orElse(null);

        createOrderIfNotExists(customer1, true, 900000, LocalDateTime.now(), false);
        createOrderIfNotExists(customer2, false, 500000, LocalDateTime.now(), false);
    }

    private void createOrderIfNotExists(Customer customer, boolean isVIP, int totalPrice, LocalDateTime bookDateTime, boolean isCancelled) {
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
                .isCancelled(isCancelled)
                .build();

        orderRepository.save(order);
        log.info("{}'s order has been added to the system", customer);
    }

    @Override
    public Order editOrderStatus(User user, UUID orderId) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderExceptions.OrderNotFound("Order with ID " + orderId + " not found"));

        order.setCancelled(!order.isCancelled());
        orderRepository.save(order);

        return order;
    }
}
