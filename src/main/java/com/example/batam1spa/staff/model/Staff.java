package com.example.batam1spa.staff.model;

import com.example.batam1spa.availability.model.TimeSlot;
import com.example.batam1spa.common.model.Auditable;
import com.example.batam1spa.common.model.Gender;
import com.example.batam1spa.service.model.ServiceType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_staffs")
public class Staff extends Auditable {
//    TODO
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(
//            name = "shift_id",
//            nullable = false,
//            referencedColumnName = "id"
//    )
//    private Shift shift

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private ServiceType serviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "start_time_slot_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private TimeSlot startTimeSlot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "end_time_slot_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private TimeSlot endTimeSlot;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isMonday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isTuesday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isWednesday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isThursday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isFriday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isSaturday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isSunday;

    @Column(
            nullable = false,
            columnDefinition = "boolean default true"
    )
    private boolean isActive;
}
