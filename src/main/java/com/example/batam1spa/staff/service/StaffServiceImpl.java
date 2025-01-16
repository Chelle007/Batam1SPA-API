package com.example.batam1spa.staff.service;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.availability.repository.TimeSlotRepository;
import com.example.batam1spa.common.model.Gender;
import com.example.batam1spa.service.model.ServiceType;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final TimeSlotRepository timeSlotRepository;

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
                .isActive(true)
                .build();

        staffRepository.save(staff);
        log.info("Staff {} has been added to the system", fullName);
    }
}
