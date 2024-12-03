package com.example.batam1spa.staff.model;

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
@Table(name = "tbl_staffs")
public class Staff extends Auditable {
    @Column(nullable = false)
    String fullName;

    // TODO: this + all other tables
}
