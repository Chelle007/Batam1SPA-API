package com.example.batam1spa.leave.dto;

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
public class CreateLeaveRequest {
    private UUID staffId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
}