package com.example.batam1spa.service.model;

import com.example.batam1spa.common.model.Auditable;
import jakarta.persistence.*;
import jakarta.validation.constraints.AssertTrue;
import lombok.*;
import org.hibernate.annotations.Check;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_service_prices")
public class ServicePrice extends Auditable {
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
