package com.example.batam1spa.order.service;

import com.example.batam1spa.order.dto.CartOrderDetailDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface CartService {
    void validateBooking(CartOrderDetailDTO cartOrderDetailDTO);
    List<CartOrderDetailDTO> getCart();
    Boolean addToCart(CartOrderDetailDTO cartOrderDetailDTO);
    Boolean removeFromCart(CartOrderDetailDTO cartOrderDetailDTO);
}
