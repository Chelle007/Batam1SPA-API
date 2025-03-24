package com.example.batam1spa.bundle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BundleSummaryDTO {
    private String bundleName;
    private int totalDuration;
    private String imgUrl;
}
