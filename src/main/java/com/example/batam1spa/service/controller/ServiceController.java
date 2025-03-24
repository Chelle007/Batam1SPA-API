package com.example.batam1spa.service.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.service.dto.*;
import com.example.batam1spa.service.model.ServiceDescription;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.service.service.ServiceDescriptionService;
import com.example.batam1spa.service.service.ServiceService;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/service")
public class ServiceController {
    private final ServiceService serviceService;
    private final ServiceDescriptionService serviceDescription;

    // Get all service members Full URI: /api/v1/service/get-all-service
    @GetMapping("/get-all-service")
    public ResponseEntity<BaseResponse<List<ServiceRequest>>> getAllServices(@AuthenticationPrincipal User user) {
        List<ServiceRequest> services = serviceService.getAllService(user);

        // Wrap the response in BaseResponse
        BaseResponse<List<ServiceRequest>> response = BaseResponse.success(
                HttpStatus.OK, services, "Fetched all services successfully");

        return ResponseEntity.ok(response);
    }

    // Get all service members Full URI: /api/v1/service/description
    @GetMapping("/description")
    public ResponseEntity<BaseResponse<List<ServiceDescription>>> getAllServicesDescription(@AuthenticationPrincipal User user) {
        List<ServiceDescription> serviceDescriptions = serviceDescription.getAllServiceDescriptions(user);

        // Wrap the response in BaseResponse
        BaseResponse<List<ServiceDescription>> response = BaseResponse.success(
                HttpStatus.OK, serviceDescriptions, "Fetched all services description successfully");

        return ResponseEntity.ok(response);
    }

    // Full URL: `POST /api/v1/service/add`
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<Service>> addService(@AuthenticationPrincipal User user, @RequestBody CreateServiceRequest createServiceRequest) {
        Service createdService = serviceService.addService(user, createServiceRequest);

        // Wrap the response in BaseResponse
        BaseResponse<Service> response = BaseResponse.success(
                HttpStatus.CREATED, createdService, "New service created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{serviceId}")
    public ResponseEntity<BaseResponse<Service>> editService(
            @AuthenticationPrincipal User user,
            @PathVariable UUID serviceId,
            @RequestBody EditServiceRequest editServiceRequest) {

        Service updatedService = serviceService.editService(user, serviceId, editServiceRequest);

        BaseResponse<Service> response = BaseResponse.success(
                HttpStatus.OK, updatedService, "Existing service updated successfully");

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/toggle-status/{serviceId}") // use PATCH for partial updates
    public ResponseEntity<BaseResponse<Service>> toggleServiceStatus(
            @AuthenticationPrincipal User user,
            @PathVariable UUID serviceId) {

        Service updatedService = serviceService.toggleServiceStatus(user, serviceId);

        BaseResponse<Service> response = BaseResponse.success(
                HttpStatus.OK, updatedService, "Service status isPublished toggled successfully");

        return ResponseEntity.ok(response);
    }

    // For customer website
    @GetMapping("/details/{lang}/{serviceId}")
    public ResponseEntity<BaseResponse<ServiceDetailsDTO>> getServiceDetails(
            @PathVariable UUID serviceId,
            @PathVariable String lang) {

        ServiceDetailsDTO serviceDetails = serviceService.getServiceDetails(serviceId, lang);

        BaseResponse<ServiceDetailsDTO> response = BaseResponse.success(
                HttpStatus.OK, serviceDetails, "Service details fetched successfully");

        return ResponseEntity.ok(response);
    }

    // Pagination API for services with shortest and longest durations with search query
    @GetMapping("/get-service-page")
    public ResponseEntity<BaseResponse<GetServicesPaginationResponse>> getServicesByPage(
            @RequestParam(defaultValue = "10") int amountPerPage,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String searchQuery) {

        GetServicesPaginationResponse servicesResponse = serviceService.getServicesByPage(amountPerPage, page, searchQuery);

        return ResponseEntity.ok(BaseResponse.success(
                HttpStatus.OK, servicesResponse, "Fetched services successfully"));
    }

}
