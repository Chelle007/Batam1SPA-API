package com.example.batam1spa.staff.service;

import com.example.batam1spa.staff.dto.CreateStaffRequest;
import com.example.batam1spa.staff.dto.EditStaffRequest;
import com.example.batam1spa.staff.dto.StaffDTO;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.user.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface StaffService {
    void seedStaff();
    List<Staff> getAllStaff(User user);
    List<String> getStaffNames(User user);
    Staff addStaff(User user, CreateStaffRequest createStaffRequestDTO);
    Staff editStaff(User user, UUID staffId, EditStaffRequest editStaffRequest);
    Page<StaffDTO> getStaffsByPage(User user, int page, int size);
}
