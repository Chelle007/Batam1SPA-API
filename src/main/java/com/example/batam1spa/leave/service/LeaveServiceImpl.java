package com.example.batam1spa.leave.service;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.common.model.Gender;
import com.example.batam1spa.leave.dto.CreateLeaveRequest;
import com.example.batam1spa.leave.dto.EditLeaveRequest;
import com.example.batam1spa.leave.model.Leave;
import com.example.batam1spa.leave.repository.LeaveRepository;
import com.example.batam1spa.staff.repository.StaffRepository;
import com.example.batam1spa.service.model.ServiceType;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.staff.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final StaffRepository staffRepository;
    private final ModelMapper modelMapper;

    @Override
    public void seedLeave() {
        // Fetch staff members by their IDs (assuming they are already seeded)
        UUID staffId1 = staffRepository.findByFullName("Helen")
                .orElseThrow(() -> new RuntimeException("Staff Helen not found"))
                .getId();
        UUID staffId2 = staffRepository.findByFullName("Ika")
                .orElseThrow(() -> new RuntimeException("Staff Ika not found"))
                .getId();
        UUID staffId3 = staffRepository.findByFullName("Abi")
                .orElseThrow(() -> new RuntimeException("Staff Abi not found"))
                .getId();

        // Seed leave data
        createLeaveIfNotExists(staffId1, LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 5), "Annual leave");
        createLeaveIfNotExists(staffId2, LocalDate.of(2023, 11, 15), LocalDate.of(2023, 11, 20), "Sick leave");
        createLeaveIfNotExists(staffId3, LocalDate.of(2023, 12, 10), LocalDate.of(2023, 12, 15), "Family event");
    }

    private void createLeaveIfNotExists(UUID staffId, LocalDate startDate, LocalDate endDate, String reason) {
        boolean leaveExists = leaveRepository.existsByStaffIdAndStartDateAndEndDate(staffId, startDate, endDate);

        if (leaveExists) {
            log.info("Leave for staff ID {} from {} to {} already exists in the system", staffId, startDate, endDate);
            return;
        }

        Leave leave = Leave.builder()
                .staffId(staffId)
                .startDate(startDate)
                .endDate(endDate)
                .reason(reason)
                .build();

        leaveRepository.save(leave);
        log.info("Leave for staff ID {} from {} to {} has been added to the system", staffId, startDate, endDate);
    }

    // Get all leave
    @Override
    public List<Leave> getAllLeave() {
        return leaveRepository.findAll();
    }

    // Add new leave
    @Override
    public Leave addLeave(UUID staffId, CreateLeaveRequest createLeaveRequestDTO) {
        // DTO entity conversion
        Leave createLeaveEntity = modelMapper.map(createLeaveRequestDTO, Leave.class);
        return leaveRepository.save(createLeaveEntity);
    }

    // Edit existing leave
    @Override
    public Leave editLeave(UUID leaveId, EditLeaveRequest editLeaveRequest) {
        // Find the existing leave member
        Leave existingLeave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + leaveId));

        // Update the leave info
        modelMapper.map(editLeaveRequest, existingLeave);

        // Save the updated leave info
        return leaveRepository.save(existingLeave);
    }

    // Delete existing leave
    @Override
    public Leave deleteLeave(UUID leaveId) {
        // Find the leave record by its ID
        Leave leaveToDelete = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + leaveId));

        // Delete the leave record
        leaveRepository.delete(leaveToDelete);

        // Log the deletion
        log.info("Leave with ID {} has been deleted", leaveId);

        return leaveToDelete;
    }
}
