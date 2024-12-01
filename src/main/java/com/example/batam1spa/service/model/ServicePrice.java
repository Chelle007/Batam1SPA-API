package com.example.batam1spa.service.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_service_prices")
public class ServicePrice {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "service_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Service service;

    @Column(nullable = false)
    private int duration;

    @Column(nullable = false)
    private int localPrice;

    @Column(nullable = false)
    private int touristPrice;
}
