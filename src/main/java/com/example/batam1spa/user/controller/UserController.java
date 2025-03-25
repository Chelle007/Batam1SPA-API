package com.example.batam1spa.user.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.user.dto.CreateUserRequest;
import com.example.batam1spa.user.dto.GetUserPaginationResponse;
import com.example.batam1spa.user.model.User;
import com.example.batam1spa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/get-user-page")
    public ResponseEntity<BaseResponse<GetUserPaginationResponse>> getUsersByPage(
            @AuthenticationPrincipal User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "true") boolean includeInactive) {
        GetUserPaginationResponse response = userService.getUsersByPage(user, page, size, includeInactive);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get Users By Page"));
    }

    @PostMapping("/change-user-status/{userId}")
    public ResponseEntity<BaseResponse<Boolean>> changeUserStatus(@AuthenticationPrincipal User user, @PathVariable UUID userId) {
        Boolean response = userService.changeUserStatus(user, userId);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Change User Status"));
    }

    @PostMapping("/add-user")
    public ResponseEntity<BaseResponse<Boolean>> addUser(@AuthenticationPrincipal User user, CreateUserRequest createUserRequest) {
        Boolean response = userService.addUser(user, createUserRequest);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Adding User"));
    }

}
