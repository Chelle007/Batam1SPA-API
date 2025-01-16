package com.example.batam1spa.service.model;

import lombok.Getter;

@Getter
public enum ServiceType {

    MASSAGE("MASSAGE"),
    MANIPEDI("MANIPEDI");

    private final String name;

    ServiceType(String name) {
        this.name = name;
    }
}
