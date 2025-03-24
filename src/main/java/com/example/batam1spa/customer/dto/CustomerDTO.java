package com.example.batam1spa.customer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CustomerDTO {
    private UUID customerId;
    private String fullName;
    private String phoneNumber;
    private String email;
    private boolean isLocal;
    private boolean isSubscribed;
}