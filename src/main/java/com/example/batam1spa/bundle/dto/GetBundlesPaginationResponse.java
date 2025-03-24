package com.example.batam1spa.bundle.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@Builder
public class GetBundlesPaginationResponse {
    private List<BundleSummaryDTO> bundles;
    private int totalPages;
    private long totalElements;
}