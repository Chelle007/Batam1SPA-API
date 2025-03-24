package com.example.batam1spa.availability.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Data
@Builder
public class GetServiceAvailabileDateRequest {
    UUID serviceId;
    int pax;
    int duration;
}
