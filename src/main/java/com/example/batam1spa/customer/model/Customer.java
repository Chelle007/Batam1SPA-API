package com.example.batam1spa.customer.model;

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
@Table(name = "tbl_customers")
public class Customer extends Auditable {
    private String fullName;

    @Column(nullable = false)
    private String phoneNumber;

    private String email;

    @Column(columnDefinition = "boolean default false")
    private boolean isLocal;
}
