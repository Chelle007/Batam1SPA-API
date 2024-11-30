package com.example.batam1spa.order.model;

import lombok.Getter;

@Getter
public enum OrderStatus {

    BOOKED("Booked"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String name;

    OrderStatus(String name) {
        this.name = name;
    }
}
