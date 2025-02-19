package com.example.batam1spa.security.service;

import com.example.batam1spa.user.model.User;

public interface RoleSecurityService {
    boolean hasRole(User user, String role);
    void checkRole(User user, String role);
}
