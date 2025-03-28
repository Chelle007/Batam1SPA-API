package com.example.batam1spa.order.service;

import com.example.batam1spa.order.dto.CheckoutRequest;
import com.example.batam1spa.order.dto.GetOrderPaginationResponse;
import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.user.model.User;

import java.time.LocalDate;
import java.util.UUID;

public interface OrderService {
    void seedOrder();
    Order editOrderStatus(User user, UUID orderId);
    Boolean checkout(CheckoutRequest checkoutRequest);
    Boolean editVIPStatus(User user, UUID orderId);
    GetOrderPaginationResponse getOrdersByPage(User user, int page, int size, LocalDate bookDate);
}
