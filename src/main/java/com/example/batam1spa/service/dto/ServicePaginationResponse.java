package com.example.batam1spa.service.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicePaginationResponse {
    private UUID serviceId;
    private String name;
    private int shortestDuration;  // Shortest duration for the range in FE
    private int longestDuration;   // Longest duration for the range in FE
    private String imgUrl;
    private boolean isPublished;
}