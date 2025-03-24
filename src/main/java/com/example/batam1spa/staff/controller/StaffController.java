package com.example.batam1spa.staff.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.staff.dto.CreateStaffRequest;
import com.example.batam1spa.staff.dto.EditStaffRequest;
import com.example.batam1spa.staff.dto.StaffDTO;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.staff.service.StaffService;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/staff")
public class StaffController {

    private final StaffService staffService;

    // Get all staff members Full URI: /api/v1/staff/get-all-staff
    @GetMapping("/get-all-staff")
    public ResponseEntity<BaseResponse<List<Staff>>> getAllStaff(@AuthenticationPrincipal User user) {
        List<Staff> allStaff = staffService.getAllStaff(user);

        // Wrap the response in BaseResponse
        BaseResponse<List<Staff>> response = BaseResponse.success(
                HttpStatus.OK, allStaff, "Fetched all staffs successfully");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/names")
    public ResponseEntity<BaseResponse<List<String>>> getStaffNames(@AuthenticationPrincipal User user) {
        List<String> staffNames = staffService.getStaffNames(user);

        BaseResponse<List<String>> response = BaseResponse.success(
                HttpStatus.OK, staffNames, "Staff names fetched successfully"
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-staff-page")
    public ResponseEntity<BaseResponse<Page<StaffDTO>>> getStaffsByPage(
            @AuthenticationPrincipal User user,
            @RequestParam int page,
            @RequestParam int size) {

        Page<StaffDTO> staffPage = staffService.getStaffsByPage(user, page, size);
        BaseResponse<Page<StaffDTO>> response = BaseResponse.success(
                HttpStatus.OK, staffPage, "Staffs fetched successfully");

        return ResponseEntity.ok(response);
    }

    // Add a new staff member Full URI: /api/v1/staff/add
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<Staff>> addStaff(@AuthenticationPrincipal User user, @RequestBody CreateStaffRequest createStaffRequestDTO) {
        Staff newStaff = staffService.addStaff(user, createStaffRequestDTO);

        // Wrap the response in BaseResponse
        BaseResponse<Staff> response = BaseResponse.success(
                HttpStatus.OK, newStaff, "Staff added successfully");

        return ResponseEntity.ok(response);
    }

    // Edit an existing staff member Full URI: /api/v1/staff/{staffId}
    @PutMapping("/edit/{staffId}")
    public ResponseEntity<BaseResponse<Staff>> editStaff(@AuthenticationPrincipal User user, @PathVariable UUID staffId, @RequestBody EditStaffRequest editStaffRequest) {
        Staff updatedStaff = staffService.editStaff(user, staffId, editStaffRequest);

        // Wrap the response in BaseResponse
        BaseResponse<Staff> response = BaseResponse.success(
                HttpStatus.OK, updatedStaff, "Staff edited successfully");
        return ResponseEntity.ok(response);
    }
}