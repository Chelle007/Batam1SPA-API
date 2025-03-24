package com.example.batam1spa.availability.service;

import com.example.batam1spa.availability.dto.*;
import com.example.batam1spa.availability.model.TimeSlot;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface AvailabilityService {
    void generateAvailabilityForNextTwoWeeks();
    int getServiceAvailability(GetServiceAvailabilityRequest getServiceAvailabilityRequest);
    List<LocalDate> getServiceAvailabileDate(GetServiceAvailabileDateRequest getServiceAvailabileDateRequest);
    List<TimeSlot> getServiceAvailabileTimeSlot(GetServiceAvailabileTimeSlotRequest getServiceAvailabileTimeSlotRequest);
    List<LocalDate> getBundleAvailabileDate(UUID bundleId);
    List<TimeSlot> getBundleAvailabileTimeSlot(UUID bundleId, LocalDate serviceDate);
}
