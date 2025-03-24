package com.example.batam1spa.order.dto;

import com.example.batam1spa.customer.model.Customer;
import com.example.batam1spa.order.model.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderResponse {
    private UUID orderId;
    private Customer customer;
    private boolean isVIP;
    private int totalPrice;
    private LocalDateTime bookDateTime;
    private boolean isCancelled;
    private List<OrderDetail> orderDetails;
}
