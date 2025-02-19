package com.example.batam1spa.order.service;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.availability.repository.TimeSlotRepository;
import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.customer.repository.CustomerRepository;
import com.example.batam1spa.order.dto.OrderDetailByServiceDateResponse;
import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.order.model.OrderDetail;
import com.example.batam1spa.order.repository.OrderDetailRepository;
import com.example.batam1spa.order.repository.OrderRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.repository.ServiceRepository;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class OrderDetailServiceImpl implements OrderDetailService {
    private final RoleSecurityService roleSecurityService;
    private final OrderDetailRepository orderDetailRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ServiceRepository serviceRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ModelMapper modelMapper;

    @Override
    public void seedOrderDetail() {
        Service service1 = serviceRepository.findByName("Head Massage").orElse(null);
        Service service2 = serviceRepository.findByName("Body Massage").orElse(null);
        Service service3 = serviceRepository.findByName("Foot Massage").orElse(null);
        Service service4 = serviceRepository.findByName("Manipedi").orElse(null);

        Customer customer1 = customerRepository.findByPhoneNumber("12345678").orElse(null);
        Customer customer2 = customerRepository.findByPhoneNumber("87654321").orElse(null);

        Order order1 = orderRepository.findByCustomer(customer1);
        Order order2 = orderRepository.findByCustomer(customer2);

        TimeSlot startTimeSlot1 = timeSlotRepository.findByLocalTime(LocalTime.of(9, 0));
        TimeSlot endTimeSlot1 = timeSlotRepository.findByLocalTime(LocalTime.of(10, 30));
        TimeSlot startTimeSlot2 = timeSlotRepository.findByLocalTime(LocalTime.of(13, 0));
        TimeSlot endTimeSlot2 = timeSlotRepository.findByLocalTime(LocalTime.of(14, 0));

        createOrderDetailIfNotExists(order1, service1, null, startTimeSlot1, endTimeSlot1, true);
        createOrderDetailIfNotExists(order1, service2, null, startTimeSlot1, endTimeSlot1, true);
        createOrderDetailIfNotExists(order2, service3, null, startTimeSlot2, endTimeSlot2, false);
        createOrderDetailIfNotExists(order2, service4, null, startTimeSlot2, endTimeSlot2, false);
    }

    private void createOrderDetailIfNotExists(Order order, Service service, Staff staff, TimeSlot startTimeSlot, TimeSlot endTimeSlot, boolean isCompleted) {
        boolean orderDetailExists = orderDetailRepository.existsByOrderAndService(order, service);

        if (orderDetailExists) {
            log.info("order detail with order {} and service {} already exists in the system", order, service);
            return;
        }

        OrderDetail orderDetail = OrderDetail.builder()
                .order(order)
                .service(service)
                .staff(staff)
                .serviceDate(LocalDate.now())
                .startTimeSlot(startTimeSlot)
                .endTimeSlot(endTimeSlot)
                .isCompleted(isCompleted)
                .build();

        orderDetailRepository.save(orderDetail);
        log.info("order detail with order {} and service {} has been added to the system", order, service);
    }

    @Override
    public List<OrderDetailByServiceDateResponse> getOrderDetailsByServiceDate(User user, LocalDate serviceDate) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        List<OrderDetail> orderDetails = orderDetailRepository.findByServiceDate(serviceDate);
        List<OrderDetailByServiceDateResponse> orderDetailByServiceDateResponses = new ArrayList<>();

        for (OrderDetail orderDetail : orderDetails) {
            OrderDetailByServiceDateResponse orderDetailByServiceDateResponse = modelMapper.map(orderDetail, OrderDetailByServiceDateResponse.class);
            Order order = orderDetail.getOrder();
            orderDetailByServiceDateResponse.setCustomer(order.getCustomer());
            orderDetailByServiceDateResponse.setVIP(order.isVIP());
            orderDetailByServiceDateResponse.setTotalPrice(order.getTotalPrice());
            orderDetailByServiceDateResponse.setCancelled(order.isCancelled());
            orderDetailByServiceDateResponses.add(orderDetailByServiceDateResponse);
        }

        return orderDetailByServiceDateResponses;
    }
}
