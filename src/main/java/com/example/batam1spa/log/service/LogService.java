package com.example.batam1spa.log.service;

import com.example.batam1spa.log.model.LogType;
import com.example.batam1spa.user.model.UserRole;

import java.io.ByteArrayInputStream;

public interface LogService {
    ByteArrayInputStream generateLogCsv();
    void addLog(String username, UserRole role, LogType type, String details);
}
