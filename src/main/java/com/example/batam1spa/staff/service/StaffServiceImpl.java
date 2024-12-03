package com.example.batam1spa.staff.service;

import com.example.batam1spa.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;

    @Override
    public void seedStaff() {

    }

    private void createStaffIfNotExists() {

    }
}
