package com.example.batam1spa.bundle.model;

import com.example.batam1spa.common.model.Auditable;
import com.example.batam1spa.service.model.ServicePrice;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_bundle_details")
public class BundleDetail extends Auditable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "bundle_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Bundle bundle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "service_price_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private ServicePrice servicePrice;

    @Column(nullable = false)
    int quantity;
}
