package com.example.batam1spa.common.model;

import lombok.Getter;

@Getter
public enum Gender {

    FEMALE("FEMALE"),
    MALE("MALE");

    private final String name;

    Gender(String name) {
        this.name = name;
    }
}
