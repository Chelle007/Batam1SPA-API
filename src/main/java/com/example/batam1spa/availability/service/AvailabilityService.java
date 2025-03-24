package com.example.batam1spa.availability.service;

import com.example.batam1spa.availability.dto.GetServiceAvailabileDateRequest;
import com.example.batam1spa.availability.dto.GetServiceAvailabileTimeSlotRequest;
import com.example.batam1spa.availability.dto.GetServiceAvailabilityRequest;
import com.example.batam1spa.availability.model.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public interface AvailabilityService {
    void generateAvailabilityForNextTwoWeeks();
    int getServiceAvailability(GetServiceAvailabilityRequest getServiceAvailabilityRequest);
    List<LocalDate> getServiceAvailabileDate(GetServiceAvailabileDateRequest getServiceAvailabileDateRequest);
    List<TimeSlot> getServiceAvailabileTimeSlot(GetServiceAvailabileTimeSlotRequest getServiceAvailabileTimeSlotRequest);
}
