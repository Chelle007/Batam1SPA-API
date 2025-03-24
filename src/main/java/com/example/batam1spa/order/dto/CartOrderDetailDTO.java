package com.example.batam1spa.order.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Data
@Builder
public class CartOrderDetailDTO {
    private UUID serviceId;
    private UUID startTimeSlotId;
    private UUID endTimeSlotId;
    private LocalDate serviceDate;
    private int qty;
}
