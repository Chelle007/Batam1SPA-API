package com.example.batam1spa.log.repository;

import com.example.batam1spa.log.model.AdminLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LogRepository extends JpaRepository<AdminLog, UUID> {
}
