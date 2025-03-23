package com.example.batam1spa.bundle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
public class BundleDTO {
    private UUID bundleId;
    private String bundleName;
    private int totalDuration;
    private Map<String, BundleContentDTO> bundleContent; // {serviceName: {duration, quantity}}
    private String imgUrl;
    private int localPrice;
    private int touristPrice;
    private boolean isPublished;
}
