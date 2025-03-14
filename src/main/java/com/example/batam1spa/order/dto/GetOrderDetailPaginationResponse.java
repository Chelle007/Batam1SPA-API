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
public class GetOrderDetailPaginationResponse {
    List<GetOrderDetailResponse> getOrderDetailResponseList;
    int page;
    int size;
    int totalPages;
}
