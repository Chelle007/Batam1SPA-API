package com.example.batam1spa.leave.repository;

import com.example.batam1spa.leave.model.Leave;
import com.example.batam1spa.staff.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;
import java.time.LocalDate;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, UUID> {
    boolean existsByStaffAndStartDateAndEndDate(Staff staff, LocalDate startDate, LocalDate endDate);
    List<Leave> findByStaff(Staff staff);
}