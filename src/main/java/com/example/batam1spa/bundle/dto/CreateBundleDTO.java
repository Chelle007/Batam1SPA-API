package com.example.batam1spa.bundle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;

@Getter
@Setter
@Builder
public class CreateBundleDTO {
    private String bundleName;
    private String imgUrl;
    private int localPrice;
    private int touristPrice;
    private boolean isPublished;
    private int quantity;
    private Map<String, Integer> bundleContent;

    // Language-specific included item descriptions
    private String IDIncludedItemDescription;
    private String ENIncludedItemDescription;

    // Language-specific service descriptions
    private String IDDescription;
    private String ENDescription;
}
