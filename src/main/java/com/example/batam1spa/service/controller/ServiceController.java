package com.example.batam1spa.service.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.service.dto.CreateServiceRequest;
import com.example.batam1spa.service.dto.EditServiceRequest;
import com.example.batam1spa.service.dto.ServiceRequest;
import com.example.batam1spa.service.model.ServiceDescription;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.service.ServiceDescriptionService;
import com.example.batam1spa.service.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;
    private final ServiceDescriptionService serviceDescription;

    // Get all service members Full URI: /api/v1/service/get-all-service
    @GetMapping("/get-all-service")
    public ResponseEntity<BaseResponse<List<ServiceRequest>>> getAllServices() {
        List<ServiceRequest> services = serviceService.getAllService();

        // Wrap the response in BaseResponse
        BaseResponse<List<ServiceRequest>> response = BaseResponse.success(
                HttpStatus.OK, services, "Fetched all services successfully");

        return ResponseEntity.ok(response);
    }

    // Get all service members Full URI: /api/v1/service/description
    @GetMapping("/description")
    public ResponseEntity<BaseResponse<List<ServiceDescription>>> getAllServicesDescription() {
        List<ServiceDescription> serviceDescriptions = serviceDescription.getAllServiceDescriptions();

        // Wrap the response in BaseResponse
        BaseResponse<List<ServiceDescription>> response = BaseResponse.success(
                HttpStatus.OK, serviceDescriptions, "Fetched all services description successfully");

        return ResponseEntity.ok(response);
    }

    // Full URL: `POST /api/v1/service/add`
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<Service>> addService(@RequestBody CreateServiceRequest createServiceRequest) {
        Service createdService = serviceService.addService(createServiceRequest);

        // Wrap the response in BaseResponse
        BaseResponse<Service> response = BaseResponse.success(
                HttpStatus.CREATED, createdService, "New service created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{serviceId}")
    public ResponseEntity<BaseResponse<Service>> editService(
            @PathVariable UUID serviceId,
            @RequestBody EditServiceRequest editServiceRequest) {

        Service updatedService = serviceService.editService(serviceId, editServiceRequest);

        BaseResponse<Service> response = BaseResponse.success(
                HttpStatus.OK, updatedService, "Existing service updated successfully");

        return ResponseEntity.ok(response);
    }

}
