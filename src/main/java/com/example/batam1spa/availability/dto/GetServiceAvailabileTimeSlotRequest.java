package com.example.batam1spa.availability.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetServiceAvailabileTimeSlotRequest {
    UUID serviceId;
    int pax;
    int duration;
    LocalDate serviceDate;
}
