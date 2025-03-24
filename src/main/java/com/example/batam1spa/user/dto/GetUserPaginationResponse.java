package com.example.batam1spa.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetUserPaginationResponse {
    List<GetUserResponse> getUserResponseList;
    int page;
    int size;
    int totalPages;
}
