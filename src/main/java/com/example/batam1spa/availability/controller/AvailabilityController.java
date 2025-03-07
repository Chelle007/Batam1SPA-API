package com.example.batam1spa.availability.controller;

import com.example.batam1spa.availability.dto.GetServiceAvailabilityRequest;
import com.example.batam1spa.availability.service.AvailabilityService;
import com.example.batam1spa.common.dto.BaseResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/availability")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    @GetMapping("/get-service-availability")
    public ResponseEntity<BaseResponse<Boolean>> getServiceAvailability(GetServiceAvailabilityRequest getServiceAvailabilityRequest) {
        Boolean response = availabilityService.getServiceAvailability(getServiceAvailabilityRequest);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Service Availability"));
    }
}
