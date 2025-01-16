package com.example.batam1spa.availability.model;

import com.example.batam1spa.common.model.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_time_slots")
public class TimeSlot extends Auditable {
    @Column(nullable = false)
    private LocalTime localTime;
}
