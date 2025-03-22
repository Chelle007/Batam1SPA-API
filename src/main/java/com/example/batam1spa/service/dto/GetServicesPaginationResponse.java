package com.example.batam1spa.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GetServicesPaginationResponse {
    private List<ServicePaginationResponse> services;
    private int totalPages;
    private long totalElements; // num of total services
}
