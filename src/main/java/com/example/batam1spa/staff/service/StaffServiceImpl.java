package com.example.batam1spa.staff.service;

import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.staff.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDate;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    @Override
    public Staff addStaff(Staff staff) {
        return staffRepository.save(staff);
    }
}
