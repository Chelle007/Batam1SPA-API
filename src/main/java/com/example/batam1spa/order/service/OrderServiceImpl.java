package com.example.batam1spa.order.service;

import com.example.batam1spa.availability.model.Availability;
import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.availability.repository.AvailabilityRepository;
import com.example.batam1spa.availability.repository.TimeSlotRepository;
import com.example.batam1spa.common.service.CommonService;
import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.customer.repository.CustomerRepository;
import com.example.batam1spa.log.model.LogType;
import com.example.batam1spa.log.service.LogService;
import com.example.batam1spa.order.dto.*;
import com.example.batam1spa.order.exception.OrderExceptions;
import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.order.model.OrderDetail;
import com.example.batam1spa.order.repository.OrderDetailRepository;
import com.example.batam1spa.order.repository.OrderRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.model.ServicePrice;
import com.example.batam1spa.service.repository.ServicePriceRepository;
import com.example.batam1spa.service.repository.ServiceRepository;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.Phonenumber;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RoleSecurityService roleSecurityService;
    private final CartService cartService;
    private final LogService logService;
    private final CommonService commonService;
    private final ServiceRepository serviceRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final AvailabilityRepository availabilityRepository;
    private final ServicePriceRepository servicePriceRepository;
    private final ModelMapper modelMapper;
    private static final PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
    private final JavaMailSender javaMailSender;

    @Override
    public void seedOrder() {
        Customer customer1 = customerRepository.findByPhoneNumber("12345678").orElse(null);
        Customer customer2 = customerRepository.findByPhoneNumber("87654321").orElse(null);

        createOrderIfNotExists(customer1, true, 900000, 990000, LocalDateTime.now(), false);
        createOrderIfNotExists(customer2, false, 500000, 550000, LocalDateTime.now(), false);
    }

    private void createOrderIfNotExists(Customer customer, boolean isVIP, int localTotalPrice, int touristTotalPrice, LocalDateTime bookDateTime, boolean isCancelled) {
        boolean orderExists = orderRepository.existsByCustomer(customer);

        if (orderExists) {
            log.info("{}'s order already exists in the system", customer);
            return;
        }

        Order order = Order.builder()
                .customer(customer)
                .isVIP(isVIP)
                .localTotalPrice(localTotalPrice)
                .touristTotalPrice(touristTotalPrice)
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

        logService.addLog(user.getUsername(), user.getManagementLevel(), LogType.UPDATE, "edit order isCancelled status to " + order.isCancelled() + " (order id: " + orderId + ")");
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

    private String generateInvoiceContent(Order order, List<OrderDetail> orderDetails) {
        StringBuilder sb = new StringBuilder();
        sb.append("Dear ").append(order.getCustomer().getFullName()).append(",\n\n");
        sb.append("Thank you for your booking! Here are your order details:\n\n");

        for (OrderDetail detail : orderDetails) {
            sb.append("🔹 Service: ").append(detail.getService().getName()).append("\n");
            sb.append("📅 Date: ").append(detail.getServiceDate()).append("\n");
            sb.append("🕒 Time: ").append(detail.getStartTimeSlot().getLocalTime()).append(" - ").append(detail.getEndTimeSlot().getLocalTime()).append("\n");
            sb.append("-----------------------------\n");
        }

        sb.append("💰 Total Price: Rp").append(order.getLocalTotalPrice()).append(" (local) | Rp").append(order.getTouristTotalPrice()).append(" (tourist)").append("\n\n");
        sb.append("Best regards,\nBatam1SPA");

        return sb.toString();
    }

    private void sendInvoice(Order order, List<OrderDetail> orderDetails) {
        try {
            String subject = "Your Invoice - Order #" + order.getId();
            String invoiceContent = generateInvoiceContent(order, orderDetails);
            Customer customer = order.getCustomer();

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(customer.getEmail());
            message.setSubject(subject);
            message.setText(invoiceContent);

            javaMailSender.send(message);
            log.info("Invoice email sent successfully to {}", customer.getEmail());
        } catch (MailException e) {
            log.error("Failed to send invoice email: {}", e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public Boolean checkout(CheckoutRequest checkoutRequest) {
        // QTY VALIDATION
        int total_qty = 0;
        for (CartOrderDetailDTO cart : checkoutRequest.getCartList()) {
            total_qty += cart.getQty();
        }
        if (total_qty > 4) {
            throw new OrderExceptions.QtyMoreThanFour("Total qty cannot be more than 4: " + total_qty);
        }

        // UPDATE CUSTOMER
        String phoneNumber = checkoutRequest.getPhonePrefix() + " " + checkoutRequest.getPhoneLocalNumber().strip();
        if (!isValidPhoneNumber(phoneNumber.strip())) {
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

        // CHECKOUT
        Order order = Order.builder()
                .customer(customer)
                .isVIP(checkoutRequest.isVIP())
                .bookDateTime(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        int localTotalPrice = 0;
        int touristTotalPrice = 0;
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartOrderDetailDTO cart : checkoutRequest.getCartList()) {
            // VALIDATE CART & REDUCE AVAILABILITY
            List<Availability> availabilityList = cartService.validateBooking(cart);
            List<Availability> updatedAvailabilities = new ArrayList<>();
            for (Availability availability : availabilityList) {
                availability.setCount(availability.getCount()-cart.getQty());
                updatedAvailabilities.add(availability);
            }
            availabilityRepository.saveAll(updatedAvailabilities);

            // ADD ORDER DETAILS TO DATABASE
            Service service = serviceRepository.findById(cart.getServiceId()).orElseThrow(() -> new OrderExceptions.ServiceNotFound("Service with ID: " + cart.getServiceId() + " not found."));
            TimeSlot startTimeSlot = timeSlotRepository.findById(cart.getStartTimeSlotId()).orElseThrow(() -> new OrderExceptions.TimeSlotNotFound("Start time slot with ID: " + cart.getStartTimeSlotId() + " not found."));
            TimeSlot endTimeSlot = timeSlotRepository.findById(cart.getEndTimeSlotId()).orElseThrow(() -> new OrderExceptions.TimeSlotNotFound("End time slot with ID: " + cart.getEndTimeSlotId() + " not found."));
            for (int i = 0; i < cart.getQty(); i++) {
                OrderDetail orderDetail = OrderDetail.builder()
                        .order(order)
                        .service(service)
                        .serviceDate(cart.getServiceDate())
                        .startTimeSlot(startTimeSlot)
                        .endTimeSlot(endTimeSlot)
                        .build();
                orderDetailRepository.save(orderDetail);
                orderDetails.add(orderDetail);
            }

            // CALCULATE TOTAL PRICE
            Duration duration = Duration.between(startTimeSlot.getLocalTime(), endTimeSlot.getLocalTime());
            long minutes = duration.toMinutes();
            ServicePrice servicePrice = servicePriceRepository.findByServiceAndDuration(service, (int) minutes).orElseThrow();
            localTotalPrice += servicePrice.getLocalPrice() * cart.getQty();
            touristTotalPrice += servicePrice.getTouristPrice() * cart.getQty();
        }

        // ADD ORDER TO DATABASE
        order.setLocalTotalPrice(localTotalPrice);
        order.setTouristTotalPrice(touristTotalPrice);
        orderRepository.save(order);

        // SEND INVOICE
        sendInvoice(order, orderDetails);

        return Boolean.TRUE;
    }

    @Override
    public Boolean editVIPStatus(User user, UUID orderId) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderExceptions.OrderNotFound("Order with ID: " + orderId + " not found"));

        order.setVIP(!order.isVIP());
        orderRepository.save(order);

        logService.addLog(user.getUsername(), user.getManagementLevel(), LogType.UPDATE, "edit VIP status to " + order.isVIP() + " (order id: " + orderId + ")");
        return Boolean.TRUE;
    }

    @Override
    public GetOrderPaginationResponse getOrdersByPage(User user, int page, int amountPerPage, LocalDate bookDate) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        commonService.validatePagination(page, amountPerPage);

        Pageable pageable = PageRequest.of(page, amountPerPage, Sort.by("bookDateTime").descending());
        Page<Order> orders;

        if (bookDate != null) {
            orders = orderRepository.findByBookDateTimeBetween(
                    bookDate.atStartOfDay(), bookDate.plusDays(1).atStartOfDay(), pageable);
        } else {
            orders = orderRepository.findAll(pageable);
        }

        List<GetOrderResponse> orderResponses = orders.stream().map(order -> {
                    GetOrderResponse response = modelMapper.map(order, GetOrderResponse.class);
                    response.setOrderDetails(orderDetailRepository.findByOrder(order));
                    return response;
                })
                .collect(Collectors.toList());

        return GetOrderPaginationResponse.builder()
                .getOrderResponseList(orderResponses)
                .page(page)
                .size(amountPerPage)
                .totalPages(orders.getTotalPages())
                .build();
    }
}
