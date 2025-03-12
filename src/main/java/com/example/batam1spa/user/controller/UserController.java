package com.example.batam1spa.user.controller;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.user.dto.CreateUserRequest;
import com.example.batam1spa.user.model.User;
import com.example.batam1spa.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/get-all-users")
    public ResponseEntity<BaseResponse<List<User>>> getAllUsers(@AuthenticationPrincipal User user, boolean includeInactive) {
        List<User> response = userService.getAllUsers(user, includeInactive);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Get All Users"));
    }

    @PostMapping("/change-user-status")
    public ResponseEntity<BaseResponse<Boolean>> changeUserStatus(@AuthenticationPrincipal User user, UUID userId) {
        Boolean response = userService.changeUserStatus(user, userId);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Change User Status"));
    }

    @PostMapping("/add-user")
    public ResponseEntity<BaseResponse<Boolean>> addUser(@AuthenticationPrincipal User user, CreateUserRequest createUserRequest) {
        Boolean response = userService.addUser(user, createUserRequest);
        return ResponseEntity.ok(BaseResponse.success(HttpStatus.OK, response, "Success Adding User"));
    }

}
