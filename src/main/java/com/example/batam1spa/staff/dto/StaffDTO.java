package com.example.batam1spa.staff.dto;

import lombok.Data;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class StaffDTO {
    private UUID staffId;
    private String fullName;
    private String gender;
    private String serviceType;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isMonday;
    private boolean isTuesday;
    private boolean isWednesday;
    private boolean isThursday;
    private boolean isFriday;
    private boolean isSaturday;
    private boolean isSunday;
    private boolean isWorking;
}
