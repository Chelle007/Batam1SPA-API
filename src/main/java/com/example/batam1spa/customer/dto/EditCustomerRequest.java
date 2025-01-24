package com.example.batam1spa.customer.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class EditCustomerRequest {
    private boolean isLocal;
}
