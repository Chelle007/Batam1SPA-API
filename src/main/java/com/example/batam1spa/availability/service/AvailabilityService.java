package com.example.batam1spa.availability.service;

import com.example.batam1spa.availability.dto.GetServiceAvailabilityRequest;

public interface AvailabilityService {
    void generateAvailabilityForNextTwoWeeks();
    Boolean getServiceAvailability(GetServiceAvailabilityRequest getServiceAvailabilityRequest);
}
