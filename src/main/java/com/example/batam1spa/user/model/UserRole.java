package com.example.batam1spa.user.model;

import lombok.Getter;

@Getter
public enum UserRole {

    ADMIN("Admin"),
    MANAGER("Manager"),
    OWNER("Owner");

    private final String name;

    UserRole(String name) {
        this.name = name;
    }
}
