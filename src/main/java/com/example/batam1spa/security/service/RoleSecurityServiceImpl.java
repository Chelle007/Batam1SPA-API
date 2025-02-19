package com.example.batam1spa.security.service;

import com.example.batam1spa.user.model.User;

import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.GrantedAuthority;

public class RoleSecurityServiceImpl implements RoleSecurityService {
    @Override
    public boolean hasRole(User user, String role) {
        Set<String> userRoles = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        return userRoles.contains(role);
    }

    @Override
    public void checkRole(User user, String role) {
        if (!hasRole(user, role)) {
            throw new AccessDeniedException("You do not have permission to access this resource.");
        }
    }
}
