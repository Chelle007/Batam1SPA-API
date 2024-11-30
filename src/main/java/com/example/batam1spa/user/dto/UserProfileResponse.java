package com.example.batam1spa.user.dto;

import com.example.batam1spa.user.model.User;
import com.example.batam1spa.user.model.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private String fullName;
    private UserRole managementLevel;
    private String username;
    private String password;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .fullName(user.getFullName())
                .managementLevel(user.getManagementLevel())
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
