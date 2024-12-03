package com.example.batam1spa.common.model;

import lombok.Getter;

@Getter
public enum LanguageCode {

    EN("EN"),
    ID("ID");

    private final String name;

    LanguageCode(String name) {
        this.name = name;
    }
}
