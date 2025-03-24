package com.example.batam1spa.order.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.order.dto.*;
import com.example.batam1spa.order.model.Order;
import com.example.batam1spa.order.service.CartService;
import com.example.batam1spa.order.service.OrderDetailService;
import com.example.batam1spa.order.service.OrderService;
import com.example.batam1spa.user.model.User;
import jakarta.validation.Valid;
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

    @GetMapping("/get-order-detail-page")
    public ResponseEntity<BaseResponse<GetOrderDetailPaginationResponse>> getOrderDetailsByPage(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) LocalDate serviceDate) {
        GetOrderDetailPaginationResponse response = orderDetailService.getOrderDetailsByPage(user, page, size, serviceDate);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Order Details By Page"));
    }

    @GetMapping("/get-order-page")
    public ResponseEntity<BaseResponse<GetOrderPaginationResponse>> getOrdersByPage(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) LocalDate bookDate) {
        GetOrderPaginationResponse response = orderService.getOrdersByPage(user, page, size, bookDate);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Orders By Page"));
    }

    @PostMapping("/edit-order-status/{orderId}")
    public ResponseEntity<BaseResponse<Order>> editOrderStatus(@AuthenticationPrincipal User user, @PathVariable UUID orderId) {
        Order response = orderService.editOrderStatus(user, orderId);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Edit Order Status"));
    }

    @GetMapping("/get-cart")
    public ResponseEntity<BaseResponse<List<CartOrderDetailDTO>>> getCart() {
        List<CartOrderDetailDTO> response = cartService.getCart();
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Cart"));
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<BaseResponse<Boolean>> addToCart(@RequestBody CartOrderDetailDTO cartOrderDetailDTO) {
        Boolean response = cartService.addToCart(cartOrderDetailDTO);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Add To Cart"));
    }

    @PostMapping("/remove-from-cart")
    public ResponseEntity<BaseResponse<Boolean>> removeFromCart(@RequestBody CartOrderDetailDTO cartOrderDetailDTO) {
        Boolean response = cartService.removeFromCart(cartOrderDetailDTO);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Remove From Cart"));
    }

    @PostMapping("/checkout")
    public ResponseEntity<BaseResponse<Boolean>> checkout(@RequestBody @Valid CheckoutRequest checkoutRequest) {
        Boolean response = orderService.checkout(checkoutRequest);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Check Out"));
    }

    @PutMapping("/edit-vip-status/{orderId}")
    public ResponseEntity<BaseResponse<Boolean>> editVIPStatus(@AuthenticationPrincipal User user, @PathVariable UUID orderId) {
        Boolean response = orderService.editVIPStatus(user, orderId);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Edit VIP Status"));
    }

//    @PutMapping("/edit-allocated-staff/{orderDetailId}")
//    public ResponseEntity<BaseResponse<Boolean>> editAllocatedStaff(@AuthenticationPrincipal User user, @PathVariable UUID orderDetailId, @RequestParam UUID staffId) {
//        Boolean response = orderService.editAllocatedStaff(user, orderDetailId, staffId);
//        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Edit Allocated Staff"));
//    }
}
