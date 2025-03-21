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
public class EditServiceRequest {

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
service ID and
{
    "imgUrl": "updated_full_body_massage.png",
    "prices": [
        { "duration": 45, "localPrice": 150000, "touristPrice": 200000 }
    ],
    "IDIncludedItemDescription": "Handuk kualitas tinggi",
    "ENIncludedItemDescription": "High-quality towel",
    "IDDescription": "Pijat tubuh dengan teknik terbaru.",
    "ENDescription": "Body massage with the latest techniques.",
    "isPublished": false
}
 */