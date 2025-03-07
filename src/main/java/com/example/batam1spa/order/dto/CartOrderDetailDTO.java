package com.example.batam1spa.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartOrderDetailDTO {
    private UUID serviceId;
    private UUID startTimeSlotId;
    private UUID endTimeSlotId;
    private LocalDate serviceDate;
}
