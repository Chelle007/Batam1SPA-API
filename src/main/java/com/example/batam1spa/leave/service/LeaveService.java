package com.example.batam1spa.leave.service;

import com.example.batam1spa.leave.dto.CreateLeaveRequest;
import com.example.batam1spa.leave.dto.EditLeaveRequest;
import com.example.batam1spa.leave.model.Leave;

import java.util.List;
import java.util.UUID;

public interface LeaveService {
    void seedLeave();

    List<Leave> getAllLeave();
    Leave addLeave(UUID staffId, CreateLeaveRequest createLeaveRequestDTO);
    Leave editLeave(UUID leaveId, EditLeaveRequest editLeaveRequest);
    Leave deleteLeave(UUID leaveId);
}