package com.example.batam1spa.order.service;

import com.example.batam1spa.availability.model.Availability;
import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.availability.repository.AvailabilityRepository;
import com.example.batam1spa.availability.repository.TimeSlotRepository;
import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.customer.repository.CustomerRepository;
import com.example.batam1spa.order.dto.CartOrderDetailDTO;
import com.example.batam1spa.order.dto.OrderDetailByServiceDateResponse;
import com.example.batam1spa.order.exception.OrderExceptions;
import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.order.model.OrderDetail;
import com.example.batam1spa.order.repository.OrderDetailRepository;
import com.example.batam1spa.order.repository.OrderRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.repository.ServiceRepository;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.user.model.User;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private final AvailabilityRepository availabilityRepository;
    private final ModelMapper modelMapper;
    private final HttpSession session;

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

    private void validateOrderDetail(UUID serviceId, UUID startTimeSlotId, UUID endTimeSlotId, LocalDate serviceDate) {
        Service service = serviceRepository.findById(serviceId).orElseThrow(() -> new OrderExceptions.ServiceNotFound("Service with ID: " + serviceId + " not found."));

        TimeSlot startTimeSlot = timeSlotRepository.findById(startTimeSlotId).orElseThrow(() -> new OrderExceptions.TimeSlotNotFound("TimeSlot with ID: " + startTimeSlotId + " not found."));
        TimeSlot endTimeSlot = timeSlotRepository.findById(endTimeSlotId).orElseThrow(() -> new OrderExceptions.TimeSlotNotFound("TimeSlot with ID: " + endTimeSlotId + " not found."));
        List<TimeSlot> selectedTimeSlots = timeSlotRepository.findTimeSlotsBetween(startTimeSlot.getLocalTime(), endTimeSlot.getLocalTime());
        if (selectedTimeSlots.isEmpty()) {
            throw new OrderExceptions.TimeSlotNotFound("No valid time slots found between start and end times.");
        }

        if (serviceDate.isBefore(LocalDate.now())) {
            throw new OrderExceptions.InvalidServiceSchedule("Invalid service date: " + serviceDate);
        } else if (serviceDate.isEqual(LocalDate.now()) && startTimeSlot.getLocalTime().isBefore(LocalTime.now())) {
            throw new OrderExceptions.InvalidServiceSchedule("Invalid service time: " + startTimeSlot.getLocalTime());
        }

        // loop through availabilities
        for (TimeSlot timeSlot : selectedTimeSlots) {
            Availability availability = availabilityRepository.findByDateAndTimeSlotAndServiceType(serviceDate, timeSlot, service.getServiceType()).orElseThrow(() -> new OrderExceptions.AvailabilityNotFound("Availability with service date: " + serviceDate + " and timeSlot: " + timeSlot.getLocalTime() + " and serviceType: " + service.getServiceType() + " not found."));
            if (availability.getCount() <= 0) {
                throw new OrderExceptions.AvailabilityNotFound("Service is fully booked at: " + timeSlot.getLocalTime());
            }
        }
    }

    @Override
    public List<CartOrderDetailDTO> getCart() {
        @SuppressWarnings("unchecked")
        List<CartOrderDetailDTO> cart = (List<CartOrderDetailDTO>) session.getAttribute("cart");
        return cart != null ? cart : new ArrayList<>();
    }

    @Override
    public Boolean addToCart(CartOrderDetailDTO cartOrderDetailDTO) {
        UUID serviceId = cartOrderDetailDTO.getServiceId();
        UUID startTimeSlotId = cartOrderDetailDTO.getStartTimeSlotId();
        UUID endTimeSlotId = cartOrderDetailDTO.getEndTimeSlotId();
        LocalDate serviceDate = cartOrderDetailDTO.getServiceDate();

        validateOrderDetail(serviceId, startTimeSlotId, endTimeSlotId, serviceDate);

        @SuppressWarnings("unchecked")
        List<CartOrderDetailDTO> cart = (List<CartOrderDetailDTO>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.add(cartOrderDetailDTO);
        session.setAttribute("cart", cart);

        return Boolean.TRUE;
    }

    @Override
    public Boolean removeFromCart(CartOrderDetailDTO cartOrderDetailDTO) {
        UUID serviceId = cartOrderDetailDTO.getServiceId();
        UUID startTimeSlotId = cartOrderDetailDTO.getStartTimeSlotId();
        UUID endTimeSlotId = cartOrderDetailDTO.getEndTimeSlotId();
        LocalDate serviceDate = cartOrderDetailDTO.getServiceDate();

        @SuppressWarnings("unchecked")
        List<CartOrderDetailDTO> cart = (List<CartOrderDetailDTO>) session.getAttribute("cart");
        if (cart == null || cart.isEmpty()) {
            throw new OrderExceptions.CartEmptyException("Cart is empty.");
        }

        boolean removed = cart.removeIf(item ->
                item.getServiceId().equals(serviceId) &&
                        item.getStartTimeSlotId().equals(startTimeSlotId) &&
                        item.getEndTimeSlotId().equals(endTimeSlotId) &&
                        item.getServiceDate().equals(serviceDate)
        );
        if (!removed) {
            throw new OrderExceptions.CartItemNotFoundException("Item not found in cart.");
        }
        session.setAttribute("cart", cart);

        return Boolean.TRUE;
    }
}
