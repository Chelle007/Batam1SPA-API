package com.example.batam1spa.service.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
@Builder
public class CreateServiceRequest {

    private String name;
    private String imgUrl;
    private List<PriceDTO> prices; // List of duration & prices
    private Map<String, String> includedItemDescriptionsMap;

    // Language-specific included item descriptions
    private String IDIncludedItemDescription;
    private String ENIncludedItemDescription;

    // Language-specific service descriptions
    private String IDDescription;
    private String ENDescription;
    private Boolean isPublished;

}

/* EXPECTED API REQUEST:
{
    "name": "Full Body Massage",
    "imgUrl": "full_body_massage.png",
    "prices": [
        { "duration": 30, "localPrice": 100000, "touristPrice": 150000 },
        { "duration": 60, "localPrice": 200000, "touristPrice": 250000 },
        { "duration": 90, "localPrice": 300000, "touristPrice": 350000 }
    ],
    "IDIncludedItemDescription": "Handuk yang lembut",
    "ENIncludedItemDescription": "Soft towel",
    "IDDescription": "Pijat seluruh tubuh yang menenangkan.",
    "ENDescription": "A relaxing full body massage.",
    "isPublished": true
}
 */