package com.example.batam1spa.staff.service;

import com.example.batam1spa.staff.dto.CreateStaffRequest;
import com.example.batam1spa.staff.dto.EditStaffRequest;
import com.example.batam1spa.staff.model.Staff;
import java.util.List;
import java.util.UUID;

public interface StaffService {
    void seedStaff();
    List<Staff> getAllStaff();
    Staff addStaff(CreateStaffRequest createStaffRequestDTO);
    Staff editStaff(UUID staffId, EditStaffRequest editStaffRequest);
}
