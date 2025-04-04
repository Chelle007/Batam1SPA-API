package com.example.batam1spa.staff.dto;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.common.model.Gender;
import com.example.batam1spa.service.model.ServiceType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Data
@Builder
public class CreateStaffRequest {
    private String fullName;
    private Gender gender;
    private ServiceType serviceType;
    private UUID startTimeSlotId;
    private UUID endTimeSlotId;
    private boolean isMonday;
    private boolean isTuesday;
    private boolean isWednesday;
    private boolean isThursday;
    private boolean isFriday;
    private boolean isSaturday;
    private boolean isSunday;
    private boolean isWorking;
}
