package com.example.batam1spa.user.service;

import com.example.batam1spa.user.dto.CreateUserRequest;
import com.example.batam1spa.user.dto.GetUserPaginationResponse;
import com.example.batam1spa.user.model.User;

import java.util.UUID;

public interface UserService {
    void seedUser();
    GetUserPaginationResponse getUsersByPage(User user, int page, int size, boolean includeInactive);
    Boolean changeUserStatus(User user, UUID userId);
    Boolean addUser(User user, CreateUserRequest createUserRequest);
}
