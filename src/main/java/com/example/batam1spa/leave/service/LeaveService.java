package com.example.batam1spa.leave.service;

import com.example.batam1spa.leave.dto.CreateLeaveRequest;
import com.example.batam1spa.leave.dto.EditLeaveRequest;
import com.example.batam1spa.leave.dto.PageLeaveDTO;
import com.example.batam1spa.leave.model.Leave;
import com.example.batam1spa.user.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.UUID;

public interface LeaveService {
    void seedLeave();

    List<Leave> getAllLeave(User user);
    Leave addLeave(User user, CreateLeaveRequest createLeaveRequestDTO);
    Leave editLeave(User user, UUID leaveId, EditLeaveRequest editLeaveRequest);
    Leave deleteLeave(User user, UUID leaveId);
    Page<PageLeaveDTO> getLeavesByPage(User user, int amountPerPage, int page);
}