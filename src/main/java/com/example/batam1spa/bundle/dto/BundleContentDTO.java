package com.example.batam1spa.bundle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BundleContentDTO {
    private int duration;
    private int quantity;
}