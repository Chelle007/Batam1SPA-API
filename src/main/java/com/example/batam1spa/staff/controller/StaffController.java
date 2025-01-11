package com.example.batam1spa.staff.controller;

import com.example.batam1spa.service.model.ServiceType;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    // Hardcoded data for testing
    @GetMapping()
    public List<Staff> getAllStaffs() {
        Staff staff1 = new Staff(
                1L, // staffId
                "John Doe", // fullName
                "Male", // gender
                ServiceType.MASSAGE, // serviceType
                LocalTime.of(9, 0), // startTimeId
                LocalTime.of(17, 0), // endTimeId
                true, // monday
                true, // tuesday
                true, // wednesday
                true, // thursday
                true, // friday
                false, // saturday
                false, // sunday
                5, // workCount
                true // isActive
        );

        Staff staff2 = new Staff(
                2L, // staffId
                "Jane Smith", // fullName
                "Female", // gender
                ServiceType.MENIPEDI, // serviceType
                LocalTime.of(10, 0), // startTimeId
                LocalTime.of(18, 0), // endTimeId
                true, // monday
                true, // tuesday
                true, // wednesday
                true, // thursday
                true, // friday
                false, // saturday
                false, // sunday
                3, // workCount
                true // isActive
        );

        return Arrays.asList(staff1, staff2);
    }

    // Real implementation
//    @GetMapping
//    public List<Staff> getAllStaff() {
//        return staffService.getAllStaff();
//    }

    @PostMapping
    public Staff addStaff(@RequestBody Staff staff) {
        return staffService.addStaff(staff);
    }
}