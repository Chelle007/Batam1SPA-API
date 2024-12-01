package com.example.batam1spa.order.model;

import com.example.batam1spa.common.model.Auditable;
import com.example.batam1spa.customer.model.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tbl_orders")
public class Order extends Auditable {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "customer_id",
            nullable = false,
            referencedColumnName = "id"
    )
    private Customer customer;

    @Column(nullable = false)
    private boolean isVIP;

    @Column(nullable = false)
    private int totalPrice;

    private LocalDateTime bookDateTime;

    @Column(nullable = false)
    private OrderStatus status;

    @PrePersist
    private void setDefaults() {
        if (status == null) {
            status = OrderStatus.BOOKED;
        }

        if (bookDateTime == null) {
            bookDateTime = getCreatedAt();
        }
    }
}
