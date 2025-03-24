package com.example.batam1spa.log.service;

import com.example.batam1spa.log.model.AdminLog;
import com.example.batam1spa.log.model.LogType;
import com.example.batam1spa.log.repository.LogRepository;
import com.example.batam1spa.user.model.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;

    @Override
    public ByteArrayInputStream generateLogCsv() {
        List<AdminLog> logs = logRepository.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8))) {

            writer.println("ID,username,role,timestamp,type,details");

            for (AdminLog log : logs) {
                writer.println(log.getId() + "," +
                        log.getUsername() + "," +
                        log.getUserRole() + "," +
                        log.getTimestamp() + "," +
                        log.getType() + "," +
                        log.getDetails());
            }

            writer.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("Error generating CSV", e);
        }
    }

    @Override
    public void addLog(String username, UserRole role, LogType type, String details) {
        AdminLog adminLog = AdminLog.builder()
                .username(username)
                .userRole(role)
                .timestamp(LocalDateTime.now())
                .type(type)
                .details(details)
                .build();

        logRepository.save(adminLog);
    }
}
