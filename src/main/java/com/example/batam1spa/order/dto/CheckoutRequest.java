package com.example.batam1spa.order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Data
@Builder
public class CheckoutRequest {
    String fullName;
    String phonePrefix;
    String phoneLocalNumber;

    @NotBlank
    @Email(message = "Invalid email format")
    String email;

    boolean isSubscribed;
    boolean isVIP;

    @Valid
    List<CartOrderDetailDTO> cartList;

}
