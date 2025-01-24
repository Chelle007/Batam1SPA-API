package com.example.batam1spa.leave.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.leave.dto.CreateLeaveRequest;
import com.example.batam1spa.leave.dto.EditLeaveRequest;
import com.example.batam1spa.leave.model.Leave;
import com.example.batam1spa.leave.service.LeaveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/leave")
public class LeaveController {
    private LeaveService leaveService;

    // Get all leave members Full URI: /api/leave/get-all-leave
    @GetMapping("/get-all-leave")
    public ResponseEntity<BaseResponse<List<Leave>>> getAllLeave() {
        List<Leave> allLeave = leaveService.getAllLeave();

        // Wrap the response in BaseResponse
        BaseResponse<List<Leave>> response = BaseResponse.success(
                HttpStatus.OK, allLeave, "Fetched all leaves successfully");

        return ResponseEntity.ok(response);
    }

    // Add a new leave member Full URI: /api/v1/leave/add
    @PostMapping("/add")
    public ResponseEntity<BaseResponse<Leave>> addLeave(@RequestBody CreateLeaveRequest createLeaveRequestDTO) {
        Leave newLeave = leaveService.addLeave(createLeaveRequestDTO);

        // Wrap the response in BaseResponse
        BaseResponse<Leave> response = BaseResponse.success(
                HttpStatus.OK, newLeave, "Leave added successfully");

        return ResponseEntity.ok(response);
    }

    // Edit an existing leave member Full URI: /api/v1/leave/edit/{leaveId}
    @PutMapping("/edit/{leaveId}")
    public ResponseEntity<BaseResponse<Leave>> editLeave(@PathVariable UUID leaveId, @RequestBody EditLeaveRequest editLeaveRequestDTO) {
        Leave updatedLeave = leaveService.editLeave(leaveId, editLeaveRequestDTO);

        // Wrap the response in BaseResponse
        BaseResponse<Leave> response = BaseResponse.success(
                HttpStatus.OK, updatedLeave, "Leave edited successfully");

        return ResponseEntity.ok(response);
    }

    // Delete an existing leave member Full URI: /api/v1/leave/delete/{leaveId}
    @DeleteMapping("/delete/{leaveId}")
    public ResponseEntity<BaseResponse<Leave>> deleteLeave(@PathVariable UUID leaveId) {
        // Call the service to delete the leave record
        Leave deletedLeave = leaveService.deleteLeave(leaveId);

        // Wrap the response in BaseResponse
        BaseResponse<Leave> response = BaseResponse.success(
                HttpStatus.OK, deletedLeave, "Leave deleted successfully");

        return ResponseEntity.ok(response);
    }

}