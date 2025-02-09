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
    private Map<String, String> includedItemDescriptionsMap; // Language-specific included item descriptions
    private Map<String, String> languageDescriptionMap; // Language-specific service descriptions
    private Boolean isActive;

}

/* EXPECTED JSON OUTPUT:
{
    "name": "Full Body Massage",
    "imgUrl": "full_body_massage.png",
    "prices": [
        { "duration": 30, "localPrice": 100000, "touristPrice": 150000 },
        { "duration": 60, "localPrice": 200000, "touristPrice": 250000 },
        { "duration": 90, "localPrice": 300000, "touristPrice": 350000 }
    ],
    "includedItemDescriptionsMap": {
        "EN": "Soft towel",
        "ID": "Handuk yang lembut"
    },
    "languageDescriptionMap": {
        "EN": "A relaxing full body massage.",
        "ID": "Pijat seluruh tubuh yang menenangkan."
    },
    "isActive": true
}

 */