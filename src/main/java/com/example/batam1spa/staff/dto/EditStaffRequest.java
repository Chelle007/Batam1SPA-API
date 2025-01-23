package com.example.batam1spa.staff.dto;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.service.model.ServiceType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Builder
public class EditStaffRequest {
    private TimeSlot startTimeSlot;
    private TimeSlot endTimeSlot;
    private ServiceType serviceType;
    private boolean isMonday;
    private boolean isTuesday;
    private boolean isWednesday;
    private boolean isThursday;
    private boolean isFriday;
    private boolean isSaturday;
    private boolean isSunday;
    private boolean isActive;
}