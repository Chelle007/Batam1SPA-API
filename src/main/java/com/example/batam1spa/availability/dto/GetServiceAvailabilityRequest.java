package com.example.batam1spa.availability.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetServiceAvailabilityRequest {
    UUID serviceId;
    LocalDate serviceDate;
    UUID timeId;
}
