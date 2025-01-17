package com.example.batam1spa.staff.service;

import com.example.batam1spa.staff.dto.CreateStaff;
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

    // Get all staff members
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    // Add a new staff member
    public Staff addStaff(CreateStaff createStaffDTO) {
        Staff createStaffEntity = convertToEntity(createStaffDTO);
        return staffRepository.save(createStaffEntity);
    }

    // Convert Entity to DTO
    private CreateStaff convertToDTO(Staff staff) {
        CreateStaff createStaffDTO = new CreateStaff();
        createStaffDTO.setFullName(staff.getFullName());
        createStaffDTO.setGender(staff.getGender());
        createStaffDTO.setServiceType(staff.getServiceType());
        createStaffDTO.setStartTime(staff.getStartTime());
        createStaffDTO.setEndTime(staff.getEndTime());
        createStaffDTO.setMonday(staff.isMonday());
        createStaffDTO.setTuesday(staff.isTuesday());
        createStaffDTO.setWednesday(staff.isWednesday());
        createStaffDTO.setThursday(staff.isThursday());
        createStaffDTO.setFriday(staff.isFriday());
        createStaffDTO.setSaturday(staff.isSaturday());
        createStaffDTO.setSunday(staff.isSunday());
        createStaffDTO.setActive(staff.isActive());
        return createStaffDTO;
    }

    // Convert DTO to Entity
    // Use this when converting the parameter input from FE
    private Staff convertToEntity(CreateStaff createStaffDTO) {
        Staff staff = new Staff();
        staff.setFullName(createStaffDTO.getFullName());
        staff.setGender(createStaffDTO.getGender());
        staff.setServiceType(createStaffDTO.getServiceType());
        staff.setStartTime(createStaffDTO.getStartTime());
        staff.setEndTime(createStaffDTO.getEndTime());
        staff.setMonday(createStaffDTO.isMonday());
        staff.setTuesday(createStaffDTO.isTuesday());
        staff.setWednesday(createStaffDTO.isWednesday());
        staff.setThursday(createStaffDTO.isThursday());
        staff.setFriday(createStaffDTO.isFriday());
        staff.setSaturday(createStaffDTO.isSaturday());
        staff.setSunday(createStaffDTO.isSunday());
        staff.setActive(createStaffDTO.isActive());
        return staff;
    }
}
