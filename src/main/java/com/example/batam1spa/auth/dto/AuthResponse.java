package com.example.batam1spa.auth.dto;

import lombok.Builder;
import lombok.Data;
import com.example.batam1spa.user.dto.UserProfileResponse;
import com.example.batam1spa.user.model.User;

@Data
@Builder
public class AuthResponse {

    private UserProfileResponse user;

    private String token;

    public static AuthResponse of (User user, String token) {
        return AuthResponse.builder()
                .user(UserProfileResponse.from(user))
                .token(token)
                .build();
    }

}
