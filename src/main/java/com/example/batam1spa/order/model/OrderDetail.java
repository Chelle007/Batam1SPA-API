package com.example.batam1spa.order.model;

import com.example.batam1spa.service.model.Service;
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
public class OrderDetail {
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

    // TODO: staffId

    @Column(nullable = false)
    private LocalDate serviceDate;

    // TODO: startTimeId

    // TODO: endTimeId
}
