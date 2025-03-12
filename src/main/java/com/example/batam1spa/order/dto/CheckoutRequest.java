package com.example.batam1spa.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckoutRequest {
    String fullName;
    String phonePrefix;
    String phoneLocalNumber;
    String email;
    boolean isSubscribed;
    boolean isVIP;
    List<CartOrderDetailDTO> cartList;
}
