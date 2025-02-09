package com.example.batam1spa.service.dto;

import com.example.batam1spa.service.model.ServiceType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

// For 'getService' API
@Getter
@Setter
@Data
@Builder
public class ServiceRequest {
    private UUID serviceId;
    private String name;
    private Integer duration; // In minutes
    private String imgUrl;
    private int localPrice;
    private int touristPrice;
    private Boolean isActive;
}