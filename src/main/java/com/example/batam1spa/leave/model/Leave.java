package com.example.batam1spa.leave.model;

import com.example.batam1spa.common.model.Auditable;
import com.example.batam1spa.staff.model.Staff;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_leaves")
public class Leave extends Auditable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "staff_id", // Foreign key column in the Leave table
            nullable = false,  // Every Leave must be associated with a Staff
            referencedColumnName = "id" // References the id column in the Staff table
    )
    private Staff staff; // Reference to the Staff entity

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String reason;
}
