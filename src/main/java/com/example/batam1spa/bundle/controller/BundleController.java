package com.example.batam1spa.bundle.controller;

import com.example.batam1spa.bundle.dto.BundleDTO;
import com.example.batam1spa.bundle.dto.CreateBundleDTO;
import com.example.batam1spa.bundle.dto.EditBundleDTO;
import com.example.batam1spa.bundle.model.Bundle;
import com.example.batam1spa.bundle.model.BundleDescription;
import com.example.batam1spa.bundle.service.BundleDescriptionService;
import com.example.batam1spa.bundle.service.BundleService;
import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/bundle")
@RequiredArgsConstructor
public class BundleController {
    private final BundleService bundleService;
    private final BundleDescriptionService bundleDescription;

    // Get all service members Full URI: /api/v1/bundle/get-all-bundle
    @GetMapping("/get-all-bundle")
    public ResponseEntity<BaseResponse<List<BundleDTO>>> getAllBundle(@AuthenticationPrincipal User user) {
        List<BundleDTO> bundles = bundleService.getAllBundle(user);

        // Wrap the response in BaseResponse
        BaseResponse<List<BundleDTO>> response = BaseResponse.success(
                HttpStatus.OK, bundles, "Fetched all bundles successfully");

        return ResponseEntity.ok(response);
    }

    // Get all bundle members Full URI: /api/v1/bundle/description
    @GetMapping("/description")
    public ResponseEntity<BaseResponse<List<BundleDescription>>> getAllBundlesDescription(@AuthenticationPrincipal User user) {
        List<BundleDescription> bundleDescriptions = bundleDescription.getAllBundleDescriptions(user);

        // Wrap the response in BaseResponse
        BaseResponse<List<BundleDescription>> response = BaseResponse.success(
                HttpStatus.OK, bundleDescriptions, "Fetched all bundles description successfully");

        return ResponseEntity.ok(response);
    }

    // Full URL: `POST /api/v1/bundle/add`
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<BundleDTO>> addBundle(@AuthenticationPrincipal User user, @RequestBody CreateBundleDTO createBundleDTO) {
        BundleDTO createdBundle = bundleService.addBundle(user, createBundleDTO);

        // Wrap the response in BaseResponse
        BaseResponse<BundleDTO> response = BaseResponse.success(
                HttpStatus.CREATED, createdBundle, "New bundle created successfully");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/edit/{bundleId}")
    public ResponseEntity<BaseResponse<BundleDTO>> editBundle(
            @AuthenticationPrincipal User user,
            @PathVariable UUID bundleId,
            @RequestBody EditBundleDTO editBundleDTO) {

        // Call the service layer to edit the bundle
        BundleDTO updatedBundle = bundleService.editBundle(user, bundleId, editBundleDTO);

        BaseResponse<BundleDTO> response = BaseResponse.success(
                HttpStatus.OK, updatedBundle, "Existing bundle updated successfully");

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/toggle-status/{bundleId}") // use PATCH for partial updates
    public ResponseEntity<BaseResponse<Bundle>> toggleBundleStatus(
            @AuthenticationPrincipal User user,
            @PathVariable UUID bundleId) {

        Bundle updatedBundle = bundleService.toggleBundleStatus(user, bundleId);

        BaseResponse<Bundle> response = BaseResponse.success(
                HttpStatus.OK, updatedBundle, "Bundle status isPublished toggled successfully");

        return ResponseEntity.ok(response);
    }

//    // For customer website
//    @GetMapping("/details/{lang}/{bundleId}")
//    public ResponseEntity<BaseResponse<ServiceDetailsDTO>> getServiceDetails(
//            @PathVariable String lang,
//            @PathVariable UUID serviceId) {
//
//        ServiceDetailsDTO serviceDetails = serviceService.getServiceDetails(serviceId, lang);
//
//        BaseResponse<ServiceDetailsDTO> response = BaseResponse.success(
//                HttpStatus.OK, serviceDetails, "Service details fetched successfully");
//
//        return ResponseEntity.ok(response);
//    }
//
//    // Pagination API for services with shortest and longest durations
//    @GetMapping("/get-service-page")
//    public ResponseEntity<BaseResponse<GetServicesPaginationResponse>> getServicesByPage(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size) {
//
//        GetServicesPaginationResponse response = serviceService.getServicesByPage(page, size);
//
//        // Wrap the response in BaseResponse
//        return ResponseEntity.ok(BaseResponse.success(
//                HttpStatus.OK, response, "Successfully fetched services with pagination"));
//    }
}
