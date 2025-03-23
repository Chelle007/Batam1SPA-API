package com.example.batam1spa.bundle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EditBundleDTO {
    private String bundleName;
    private String imgUrl;
    private int localPrice;
    private int touristPrice;

    // Language-specific included item descriptions
    private String IDIncludedItemDescription;
    private String ENIncludedItemDescription;

    // Language-specific service descriptions
    private String IDDescription;
    private String ENDescription;
}
