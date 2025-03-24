package com.example.batam1spa.availability.controller;

import com.example.batam1spa.availability.dto.GetServiceAvailabileDateRequest;
import com.example.batam1spa.availability.dto.GetServiceAvailabileTimeSlotRequest;
import com.example.batam1spa.availability.dto.GetServiceAvailabilityRequest;
import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.availability.service.AvailabilityService;
import com.example.batam1spa.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    @GetMapping("/get-service-availability")
    public ResponseEntity<BaseResponse<Integer>> getServiceAvailability(GetServiceAvailabilityRequest getServiceAvailabilityRequest) {
        Integer response = availabilityService.getServiceAvailability(getServiceAvailabilityRequest);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Service Availability"));
    }

    @GetMapping("/get-service-available-date")
    public ResponseEntity<BaseResponse<List<LocalDate>>> getServiceAvailabileDate(GetServiceAvailabileDateRequest getServiceAvailabileDateRequest) {
        List<LocalDate> response = availabilityService.getServiceAvailabileDate(getServiceAvailabileDateRequest);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Service Available Date"));
    }

    @GetMapping("/get-service-available-time-slot")
    public ResponseEntity<BaseResponse<List<TimeSlot>>> getServiceAvailabileTimeSlot(GetServiceAvailabileTimeSlotRequest getServiceAvailabileTimeSlotRequest) {
        List<TimeSlot> response = availabilityService.getServiceAvailabileTimeSlot(getServiceAvailabileTimeSlotRequest);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Service Available Time Slot"));
    }
}
