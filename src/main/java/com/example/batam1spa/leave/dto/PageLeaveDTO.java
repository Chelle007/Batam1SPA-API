package com.example.batam1spa.leave.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import java.time.LocalDate;

@Data
@Builder
public class PageLeaveDTO {
    private UUID leaveId;
    private String staffName;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
}