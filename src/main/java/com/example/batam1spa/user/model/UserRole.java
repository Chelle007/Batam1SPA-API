package com.example.batam1spa.user.model;

import lombok.Getter;

import java.util.Set;

@Getter
public enum UserRole {

    OWNER("Owner", Set.of("ROLE_MANAGER", "ROLE_ADMIN")),
    MANAGER("Manager", Set.of("ROLE_ADMIN")),
    ADMIN("Admin", Set.of());

    private final String name;
    private final Set<String> inheritedRoles;

    UserRole(String name, Set<String> inheritedRoles) {
        this.name = name;
        this.inheritedRoles = inheritedRoles;
    }

    public Set<String> getAllRoles() {
        Set<String> allRoles = new java.util.HashSet<>(inheritedRoles);
        allRoles.add("ROLE_" + this.name()); // Add self-role
        return allRoles;
    }
}
