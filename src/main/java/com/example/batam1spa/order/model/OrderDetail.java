package com.example.batam1spa.order.model;

import com.example.batam1spa.availability.model.Time;
import com.example.batam1spa.common.model.Auditable;
import com.example.batam1spa.service.model.Service;
import com.example.batam1spa.staff.model.Staff;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_order_details")
public class OrderDetail extends Auditable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "order_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "service_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Service service;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "staff_id",
            referencedColumnName = "id"
    )
    private Staff staff;

    @Column(nullable = false)
    private LocalDate serviceDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "time_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Time startTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "time_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Time endTime;

    @Column(
            nullable = false,
            columnDefinition = "boolean default false"
    )
    private boolean isCompleted;
}
