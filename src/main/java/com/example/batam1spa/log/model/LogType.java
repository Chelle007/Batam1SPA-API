package com.example.batam1spa.log.model;

import lombok.Getter;

@Getter
public enum LogType {

    LOGIN("LOGIN"),
    CREATE("CREATE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    private final String name;

    LogType(String name) {
        this.name = name;
    }
}
