package com.example.batam1spa.service.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_service_descriptions")
public class ServiceDescription {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "service_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Service service;

    @Column(nullable = false)
    private String languageCode;

    @Column(nullable = false)
    private String description;
}
