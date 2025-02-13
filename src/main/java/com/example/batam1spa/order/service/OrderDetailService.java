package com.example.batam1spa.order.service;

import com.example.batam1spa.order.dto.OrderDetailByServiceDateResponse;
import com.example.batam1spa.user.model.User;

import java.time.LocalDate;
import java.util.List;

public interface OrderDetailService {

    void seedOrderDetail();

//    List<OrderDetailByServiceDateResponse> getOrderDetailsByServiceDate(User user, LocalDate serviceDate);
}
