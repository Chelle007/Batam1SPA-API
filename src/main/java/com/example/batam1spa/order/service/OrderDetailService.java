package com.example.batam1spa.order.service;

import com.example.batam1spa.order.dto.GetOrderDetailPaginationResponse;
import com.example.batam1spa.user.model.User;

import java.time.LocalDate;

public interface OrderDetailService {

    void seedOrderDetail();
    GetOrderDetailPaginationResponse getOrderDetailsByPage(User user, int page, int size, LocalDate serviceDate);
}
