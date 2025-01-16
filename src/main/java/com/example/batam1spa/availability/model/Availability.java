package com.example.batam1spa.availability.model;

import com.example.batam1spa.common.model.Auditable;
import com.example.batam1spa.service.model.ServiceType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_availabilities")
public class Availability extends Auditable {
    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "time_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private TimeSlot timeSlot;

    @Column(nullable = false)
    private ServiceType serviceType;

    @Column(
            nullable = false,
            columnDefinition = "int default 0"
    )
    private int count;
}
