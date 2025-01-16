package com.example.batam1spa.staff.service;

import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService {
//    @Autowired
    private final StaffRepository staffRepository;

    @Override
    public void seedStaff() {

    }

    private void createStaffIfNotExists() {

    }

    // Get all staff members
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    // Add a new staff member
    public Staff addStaff(Staff staff) {
        return staffRepository.save(staff);
    }

}
