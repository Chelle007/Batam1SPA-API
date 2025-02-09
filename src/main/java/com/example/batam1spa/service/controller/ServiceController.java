package com.example.batam1spa.service.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.service.dto.ServiceRequest;
import com.example.batam1spa.service.service.ServiceService;
import com.example.batam1spa.staff.model.Staff;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    // Get all staff members Full URI: /api/staff/get-all-service
    @GetMapping("/get-all-service")
    public ResponseEntity<BaseResponse<List<ServiceRequest>>> getAllServices() {
        List<ServiceRequest> services = serviceService.getAllService();

        // Wrap the response in BaseResponse
        BaseResponse<List<ServiceRequest>> response = BaseResponse.success(
                HttpStatus.OK, services, "Fetched all staffs successfully");

        return ResponseEntity.ok(response);
    }


}
