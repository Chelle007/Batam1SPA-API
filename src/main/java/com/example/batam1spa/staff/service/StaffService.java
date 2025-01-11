package com.example.batam1spa.staff.service;

import com.example.batam1spa.staff.model.Staff;
import java.util.List;

public interface StaffService {
    List<Staff> getAllStaff();
    Staff addStaff(Staff staff);
}