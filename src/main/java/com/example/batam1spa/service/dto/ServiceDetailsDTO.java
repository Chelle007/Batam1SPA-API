package com.example.batam1spa.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class ServiceDetailsDTO {
    private String serviceId;
    private String description; // About section (based on language)
    private String includedItems; // What's included section (based on language)
    private List<PriceDTO> prices; // Pricing section
}