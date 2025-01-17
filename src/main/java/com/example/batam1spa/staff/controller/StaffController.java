package com.example.batam1spa.staff.controller;

import com.example.batam1spa.staff.dto.CreateStaffRequest;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/staff")
public class StaffController {

//    @Autowired
    private StaffService staffService;

    // Get all staff members Full URI: /api/staff/get-all-staff
    @GetMapping("/get-all-staff")
    public List<Staff> getAllStaff() {
        return staffService.getAllStaff();
    }

    // Add a new staff member Full URI: /api/staff/add
    @PostMapping("/add")
    public Staff addStaff(@RequestBody CreateStaffRequest createStaffRequestDTO) {
        return staffService.addStaff(createStaffRequestDTO);
    }
}