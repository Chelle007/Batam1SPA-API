package com.example.batam1spa.staff.service;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.availability.repository.TimeSlotRepository;
import com.example.batam1spa.common.model.Gender;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.service.model.ServiceType;
import com.example.batam1spa.staff.dto.CreateStaffRequest;
import com.example.batam1spa.staff.dto.EditStaffRequest;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.staff.repository.StaffRepository;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService {
    private final RoleSecurityService roleSecurityService;
    private final StaffRepository staffRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ModelMapper modelMapper;

    @Override
    public void seedStaff() {
        TimeSlot startTimeSlot1 = timeSlotRepository.findByLocalTime(LocalTime.of(9, 0));
        TimeSlot endTimeSlot1 = timeSlotRepository.findByLocalTime(LocalTime.of(15, 0));
        TimeSlot startTimeSlot2 = timeSlotRepository.findByLocalTime(LocalTime.of(15, 0));
        TimeSlot endTimeSlot2 = timeSlotRepository.findByLocalTime(LocalTime.of(21, 0));

        createStaffIfNotExists("Helen", Gender.FEMALE, ServiceType.MANIPEDI, startTimeSlot1, endTimeSlot1);
        createStaffIfNotExists("Ika", Gender.FEMALE, ServiceType.MASSAGE, startTimeSlot1, endTimeSlot1);
        createStaffIfNotExists("Abi", Gender.MALE, ServiceType.MASSAGE, startTimeSlot2, endTimeSlot2);
    }

    private void createStaffIfNotExists(String fullName, Gender gender, ServiceType serviceType, TimeSlot startTimeSlot, TimeSlot endTimeSlot) {
        boolean staffExists = staffRepository.existsByFullName(fullName);

        if (staffExists) {
            log.info("Staff named {} already exists in the system", fullName);
            return;
        }

        Staff staff = Staff.builder()
                .fullName(fullName)
                .gender(gender)
                .serviceType(serviceType)
                .startTimeSlot(startTimeSlot)
                .endTimeSlot(endTimeSlot)
                .isMonday(true)
                .isTuesday(true)
                .isWednesday(true)
                .isThursday(true)
                .isFriday(true)
                .isSaturday(true)
                .isSunday(true)
                .isWorking(true)
                .build();

        staffRepository.save(staff);
        log.info("Staff {} has been added to the system", fullName);
    }

    // Get all staff members
    @Override
    public List<Staff> getAllStaff(User user) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
        return staffRepository.findAll();
    }

    // Add a new staff member
    @Override
    public Staff addStaff(User user, CreateStaffRequest createStaffRequest) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        // DTO entity conversion
        Staff createStaffEntity = modelMapper.map(createStaffRequest, Staff.class);
        return staffRepository.save(createStaffEntity);
    }

    // Edit existing staff
    @Override
    public Staff editStaff(User user, UUID staffId, EditStaffRequest editStaffRequest) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");

        // Find the existing staff member
        Staff existingStaff = staffRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + staffId));

        // Update the staff info
        modelMapper.map(editStaffRequest, existingStaff);

        // Save the updated staff info
        return staffRepository.save(existingStaff);
    }
}
