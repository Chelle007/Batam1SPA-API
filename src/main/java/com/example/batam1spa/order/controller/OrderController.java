package com.example.batam1spa.order.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.order.dto.*;
import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.order.service.CartService;
import com.example.batam1spa.order.service.OrderDetailService;
import com.example.batam1spa.order.service.OrderService;
import com.example.batam1spa.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderDetailService orderDetailService;
    private final OrderService orderService;
    private final CartService cartService;

    @GetMapping("/get-order-details")
    public ResponseEntity<BaseResponse<GetOrderDetailPaginationResponse>> getOrderDetails(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) LocalDate serviceDate) {
        GetOrderDetailPaginationResponse response = orderDetailService.getOrderDetails(user, page, size, serviceDate);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Order Details"));
    }

    @GetMapping("/get-order")
    public ResponseEntity<BaseResponse<GetOrderPaginationResponse>> getOrders(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) LocalDate bookDate) {
        GetOrderPaginationResponse response = orderService.getOrders(user, page, size, bookDate);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Orders"));
    }

    @PostMapping("/edit-order-status")
    public ResponseEntity<BaseResponse<Order>> editOrderStatus(@AuthenticationPrincipal User user, UUID orderId) {
        Order response = orderService.editOrderStatus(user, orderId);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Edit Order Status"));
    }

    @GetMapping("/get-cart")
    public ResponseEntity<BaseResponse<List<CartOrderDetailDTO>>> getCart() {
        List<CartOrderDetailDTO> response = cartService.getCart();
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Cart"));
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<BaseResponse<Boolean>> addToCart(CartOrderDetailDTO cartOrderDetailDTO) {
        Boolean response = cartService.addToCart(cartOrderDetailDTO);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Add To Cart"));
    }

    @PostMapping("/remove-from-cart")
    public ResponseEntity<BaseResponse<Boolean>> removeFromCart(CartOrderDetailDTO cartOrderDetailDTO) {
        Boolean response = cartService.removeFromCart(cartOrderDetailDTO);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Remove From Cart"));
    }

    @PostMapping("/check-out")
    public ResponseEntity<BaseResponse<Boolean>> checkout(CheckoutRequest checkoutRequest) {
        Boolean response = orderService.checkout(checkoutRequest);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Remove From Cart"));
    }

    @PostMapping("/edit-vip-status")
    public ResponseEntity<BaseResponse<Boolean>> editVIPStatus(@AuthenticationPrincipal User user, UUID orderId) {
        Boolean response = orderService.editVIPStatus(user, orderId);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Edit VIP Status"));
    }
}
