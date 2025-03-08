package com.example.batam1spa.order.service;

import com.example.batam1spa.availability.model.Availability;
import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.availability.repository.AvailabilityRepository;
import com.example.batam1spa.availability.repository.TimeSlotRepository;
import com.example.batam1spa.order.dto.CartOrderDetailDTO;
import com.example.batam1spa.order.exception.OrderExceptions;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.repository.ServiceRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
@Slf4j
public class CartServiceImpl implements CartService {
    private final ServiceRepository serviceRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final AvailabilityRepository availabilityRepository;
    private final HttpSession session;

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
