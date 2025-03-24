package com.example.batam1spa.bundle.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BundleDetailDTO {
    private String description;
    private String includedItemDescription;
    private int duration;
    private int localPrice;
    private int touristPrice;
}
