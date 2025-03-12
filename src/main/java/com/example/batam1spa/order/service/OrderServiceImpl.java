package com.example.batam1spa.order.service;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.availability.repository.TimeSlotRepository;
import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.customer.repository.CustomerRepository;
import com.example.batam1spa.order.dto.CartOrderDetailDTO;
import com.example.batam1spa.order.dto.CheckoutRequest;
import com.example.batam1spa.order.exception.OrderExceptions;
import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.order.model.OrderDetail;
import com.example.batam1spa.order.repository.OrderDetailRepository;
import com.example.batam1spa.order.repository.OrderRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.repository.ServiceRepository;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RoleSecurityService roleSecurityService;
    private final CartService cartService;
    private final ServiceRepository serviceRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final OrderDetailRepository orderDetailRepository;
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

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

    private static boolean isValidPhoneNumber(String phoneNumber) {
        try {
            Phonenumber.PhoneNumber number = phoneNumberUtil.parse(phoneNumber, null);
            return phoneNumberUtil.isValidNumber(number);
        } catch (NumberParseException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public Boolean checkout(CheckoutRequest checkoutRequest) {
        // UPDATE CUSTOMER
        String phoneNumber = checkoutRequest.getPhonePrefix() + " " + checkoutRequest.getPhoneLocalNumber().strip();
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new OrderExceptions.InvalidPhoneNumber("Invalid phone number: " + phoneNumber);
        }

        Optional<Customer> existingCustomer = customerRepository.findByPhoneNumber(phoneNumber);
        Customer customer;
        if (existingCustomer.isPresent()) {
            customer = existingCustomer.get();

            boolean updated = false;
            if (!customer.getFullName().equals(checkoutRequest.getFullName())) {
                customer.setFullName(checkoutRequest.getFullName());
                updated = true;
            }
            if (!customer.getEmail().equals(checkoutRequest.getEmail())) {
                customer.setEmail(checkoutRequest.getEmail());
                updated = true;
            }
            if (customer.isSubscribed() != checkoutRequest.isSubscribed()) {
                customer.setSubscribed(checkoutRequest.isSubscribed());
                updated = true;
            }

            if (updated) {
                customerRepository.save(customer);
            }
        } else {
            customer = customerRepository.save(Customer.builder()
                    .fullName(checkoutRequest.getFullName())
                    .phoneNumber(phoneNumber)
                    .email(checkoutRequest.getEmail())
                    .isSubscribed(checkoutRequest.isSubscribed())
                    .build());
        }

        // CART VALIDATION
        for (CartOrderDetailDTO cart : checkoutRequest.getCartList()) {
            cartService.validateBooking(cart);
        }

        // CHECKOUT
        Order order = Order.builder()
                .customer(customer)
                .isVIP(checkoutRequest.isVIP())
                .bookDateTime(LocalDateTime.now())
                .build();

        int totalPrice = 0;
        for (CartOrderDetailDTO cart : checkoutRequest.getCartList()) {
            Service service = serviceRepository.findById(cart.getServiceId()).orElseThrow(() -> new OrderExceptions.ServiceNotFound("Service with ID: " + cart.getServiceId() + " not found."));
            TimeSlot startTimeSlot = timeSlotRepository.findById(cart.getStartTimeSlotId()).orElseThrow(() -> new OrderExceptions.TimeSlotNotFound("Start time slot with ID: " + cart.getStartTimeSlotId() + " not found."));
            TimeSlot endTimeSlot = timeSlotRepository.findById(cart.getEndTimeSlotId()).orElseThrow(() -> new OrderExceptions.TimeSlotNotFound("End time slot with ID: " + cart.getEndTimeSlotId() + " not found."));

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .service(service)
                    .serviceDate(cart.getServiceDate())
                    .startTimeSlot(startTimeSlot)
                    .endTimeSlot(endTimeSlot)
                    .build();
            orderDetailRepository.save(orderDetail);
        }

        order.setTotalPrice(totalPrice);
        orderRepository.save(order);

        // SEND INVOICE

        return Boolean.TRUE;
    }

    @Override
    public Boolean editVIPStatus(User user, UUID orderId) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderExceptions.OrderNotFound("Order with ID: " + orderId + " not found"));

        order.setVIP(!order.isVIP());
        orderRepository.save(order);
        return Boolean.TRUE;
    }
}
