package com.example.batam1spa.staff.service;

import com.example.batam1spa.staff.dto.CreateStaff;
import com.example.batam1spa.staff.model.Staff;
import java.util.List;

public interface StaffService {
    void seedStaff();
    List<Staff> getAllStaff();
    Staff addStaff(CreateStaff createStaffDTO);
}
