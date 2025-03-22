package com.example.batam1spa.service.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PriceDTO {
    private int duration;
    private int localPrice;
    private int touristPrice;
}