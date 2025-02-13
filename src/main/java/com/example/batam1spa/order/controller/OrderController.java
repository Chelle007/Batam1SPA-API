package com.example.batam1spa.order.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.order.service.OrderDetailService;
import com.example.batam1spa.order.dto.OrderDetailByServiceDateResponse;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderDetailService orderDetailService;

    @GetMapping("/get-by-service-date")
    public ResponseEntity<BaseResponse<List<OrderDetailByServiceDateResponse>>> getOrderDetailsByServiceDate(@AuthenticationPrincipal User user, LocalDate localDate) {
        List<OrderDetailByServiceDateResponse> response = orderDetailService.getOrderDetailsByServiceDate(user, localDate);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Orders By Service Date"));
    }
}
