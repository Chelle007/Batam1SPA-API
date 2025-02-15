package com.example.batam1spa.order.dto;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.staff.model.Staff;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailByServiceDateResponse {
    private UUID orderDetailId;
    private Customer customer;
    private Service service;
    private Staff staff;
    private TimeSlot startTimeSlot;
    private TimeSlot endTimeSlot;
    private boolean isVIP;
    private int totalPrice;
    private boolean isCancelled;

}
