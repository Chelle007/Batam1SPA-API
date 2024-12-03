package com.example.batam1spa.bundle.model;

import com.example.batam1spa.common.model.Auditable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_bundles")
public class Bundle extends Auditable {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private int localPrice;

    @Column(nullable = false)
    private int touristPrice;
}
