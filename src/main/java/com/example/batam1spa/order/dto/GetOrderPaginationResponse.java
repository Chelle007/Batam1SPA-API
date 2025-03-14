package com.example.batam1spa.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetOrderPaginationResponse {
    List<GetOrderResponse> getOrderResponseList;
    int page;
    int size;
    int totalPages;
}
