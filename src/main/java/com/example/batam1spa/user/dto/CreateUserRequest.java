package com.example.batam1spa.user.dto;

import com.example.batam1spa.user.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    private String fullName;
    private UserRole managementLevel;
    private String username;
    private String password;
}
