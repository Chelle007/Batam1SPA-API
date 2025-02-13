package com.example.batam1spa.order.dto;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.staff.model.Staff;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Data
@Builder
public class OrderDetailByServiceDateResponse {
    private UUID orderDetailId;
    private Customer customer;
    private Service service;
    private Staff staff;
    private TimeSlot startTimeSlot;
    private TimeSlot endTimeSlot;
    private boolean isVIP;
    private int totalPrice;

}
