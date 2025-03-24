package com.example.batam1spa.bundle.model;

import com.example.batam1spa.common.model.Auditable;
import com.example.batam1spa.common.model.LanguageCode;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_bundle_descriptions")
public class BundleDescription extends Auditable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "bundle_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Bundle bundle;

    @Column(nullable = false)
    private LanguageCode languageCode;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String includedItemDescription;
}
