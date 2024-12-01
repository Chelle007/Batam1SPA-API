package com.example.batam1spa.bundle.model;

import com.example.batam1spa.service.model.Service;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_bundle_details")
public class BundleDetail {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "bundle_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Bundle bundle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "service_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Service service;

    @Column(nullable = false)
    int quantity;
}
