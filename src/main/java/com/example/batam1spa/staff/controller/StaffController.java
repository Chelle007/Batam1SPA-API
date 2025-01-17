package com.example.batam1spa.staff.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.staff.dto.CreateStaffRequest;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.staff.service.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

    private StaffService staffService;

    // Get all staff members Full URI: /api/staff/get-all-staff
    @GetMapping("/get-all-staff")
    public ResponseEntity<BaseResponse<List<Staff>>> getAllStaff() {
        List<Staff> allStaff = staffService.getAllStaff();

        // Wrap the response in BaseResponse
        BaseResponse<List<Staff>> response = BaseResponse.success(
                HttpStatus.OK, allStaff, "Fetched all staffs successfully");

        return ResponseEntity.ok(response);
    }

    // Add a new staff member Full URI: /api/v1/staff/add
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<Staff>> addStaff(@RequestBody CreateStaffRequest createStaffRequestDTO) {
        Staff newStaff = staffService.addStaff(createStaffRequestDTO);

        // Wrap the response in BaseResponse
        BaseResponse<Staff> response = BaseResponse.success(
                HttpStatus.OK, newStaff, "Staff added successfully");

        return ResponseEntity.ok(response);
    }
}