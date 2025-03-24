package com.example.batam1spa.user.dto;

import com.example.batam1spa.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserResponse {
    private UUID userId;
    private String fullName;
    private UserRole managementLevel;
    private String username;
    private String password;
    private boolean isWorking;
}
