package com.example.batam1spa.order.service;

import com.example.batam1spa.availability.model.Availability;
import com.example.batam1spa.order.dto.CartOrderDetailDTO;

import java.util.List;

public interface CartService {
    List<Availability> validateBooking(CartOrderDetailDTO cartOrderDetailDTO);
    List<CartOrderDetailDTO> getCart();
    Boolean addToCart(CartOrderDetailDTO cartOrderDetailDTO);
    Boolean removeFromCart(CartOrderDetailDTO cartOrderDetailDTO);
}
