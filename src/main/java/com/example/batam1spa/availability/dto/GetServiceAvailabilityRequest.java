package com.example.batam1spa.availability.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Data
@Builder
public class GetServiceAvailabilityRequest {
    UUID serviceId;
    LocalDate serviceDate;
    UUID timeId;
}
