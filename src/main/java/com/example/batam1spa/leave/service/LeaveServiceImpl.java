package com.example.batam1spa.leave.service;

import com.example.batam1spa.leave.dto.CreateLeaveRequest;
import com.example.batam1spa.leave.dto.EditLeaveRequest;
import com.example.batam1spa.leave.dto.PageLeaveDTO;
import com.example.batam1spa.leave.model.Leave;
import com.example.batam1spa.leave.repository.LeaveRepository;
import com.example.batam1spa.staff.model.Staff;
import com.example.batam1spa.staff.repository.StaffRepository;
import com.example.batam1spa.security.service.RoleSecurityService;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LeaveServiceImpl implements LeaveService {
    private final RoleSecurityService roleSecurityService;
    private final LeaveRepository leaveRepository;
    private final StaffRepository staffRepository;
    private final ModelMapper modelMapper;

    @Override
    public void seedLeave() {
        // Fetch staff members by their full names (assuming they are already seeded)
        Staff staff1 = staffRepository.findByFullName("Helen")
                .orElseThrow(() -> new RuntimeException("Staff Helen not found"));
        Staff staff2 = staffRepository.findByFullName("Ika")
                .orElseThrow(() -> new RuntimeException("Staff Ika not found"));
        Staff staff3 = staffRepository.findByFullName("Abi")
                .orElseThrow(() -> new RuntimeException("Staff Abi not found"));

        // Seed leave data
        createLeaveIfNotExists(staff1, LocalDate.of(2023, 10, 1), LocalDate.of(2023, 10, 5), "Annual leave");
        createLeaveIfNotExists(staff2, LocalDate.of(2023, 11, 15), LocalDate.of(2023, 11, 20), "Sick leave");
        createLeaveIfNotExists(staff3, LocalDate.of(2023, 12, 10), LocalDate.of(2023, 12, 15), "Family event");
    }

    private void createLeaveIfNotExists(Staff staff, LocalDate startDate, LocalDate endDate, String reason) {
        boolean leaveExists = leaveRepository.existsByStaffAndStartDateAndEndDate(staff, startDate, endDate);

        if (leaveExists) {
            log.info("Leave for staff {} from {} to {} already exists in the system", staff.getFullName(), startDate, endDate);
            return;
        }

        Leave leave = Leave.builder()
                .staff(staff) // Use the Staff entity
                .startDate(startDate)
                .endDate(endDate)
                .reason(reason)
                .build();

        leaveRepository.save(leave);
        log.info("Leave for staff {} from {} to {} has been added to the system", staff.getFullName(), startDate, endDate);
    }

    // Get all leave
    @Override
    public List<Leave> getAllLeave(User user) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
        return leaveRepository.findAll();
    }

    @Override
    public Page<PageLeaveDTO> getLeavesByPage(User user, int amountPerPage, int page) {
        Pageable pageable = PageRequest.of(page, amountPerPage, Sort.by("startDate").descending());
        Page<Leave> leavePage = leaveRepository.findAll(pageable);

        List<PageLeaveDTO> leaveDTOs = leavePage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(leaveDTOs, pageable, leavePage.getTotalElements());
    }

    private PageLeaveDTO convertToDTO(Leave leave) {
        return PageLeaveDTO.builder()
                .leaveId(leave.getId())
                .staffName(leave.getStaff().getFullName())
                .reason(leave.getReason())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .build();
    }

    // Add new leave
    @Override
    public Leave addLeave(User user, CreateLeaveRequest createLeaveRequestDTO) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
        // Fetch the Staff entity using the staffId from the DTO
        Staff staff = staffRepository.findById(createLeaveRequestDTO.getStaffId())
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + createLeaveRequestDTO.getStaffId()));

        // DTO entity conversion
        Leave createLeaveEntity = modelMapper.map(createLeaveRequestDTO, Leave.class);
        createLeaveEntity.setStaff(staff); // Set the Staff entity

        return leaveRepository.save(createLeaveEntity);
    }

    // Edit existing leave
    @Override
    public Leave editLeave(User user, UUID leaveId, EditLeaveRequest editLeaveRequestDTO) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
        // Find the existing leave record
        Leave existingLeave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave not found with id: " + leaveId));

        // Update the leave info
        modelMapper.map(editLeaveRequestDTO, existingLeave);

        // Save the updated leave info
        return leaveRepository.save(existingLeave);
    }

    // Delete existing leave
    @Override
    public Leave deleteLeave(User user, UUID leaveId) {
        roleSecurityService.checkRole(user, "ROLE_ADMIN");
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