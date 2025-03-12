package com.example.batam1spa.user.service;

import com.example.batam1spa.user.dto.CreateUserRequest;
import com.example.batam1spa.user.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    void seedUser();
    List<User> getAllUsers(User user, boolean includeInactive);
    Boolean changeUserStatus(User user, UUID userId);
    Boolean addUser(User user, CreateUserRequest createUserRequest);
}
