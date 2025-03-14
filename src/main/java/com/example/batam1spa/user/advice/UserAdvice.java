package com.example.batam1spa.user.advice;

import com.example.batam1spa.user.exception.UserExceptions;
import com.example.batam1spa.common.dto.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.batam1spa.order")
public class UserAdvice {
    private static final Logger log = LoggerFactory.getLogger(UserAdvice.class);

    @ExceptionHandler(UserExceptions.UserNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleUserNotFoundException(UserExceptions.UserNotFound ex) {
        log.error("User not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "User not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserExceptions.UnauthorizedRole.class)
    public ResponseEntity<BaseResponse<String>> handleUnauthorizedRoleException(UserExceptions.UnauthorizedRole ex) {
        log.error("Unauthorized role: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.UNAUTHORIZED, "Unauthorized role");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserExceptions.UsernameAlreadyExists.class)
    public ResponseEntity<BaseResponse<String>> handleUsernameAlreadyExistsException(UserExceptions.UsernameAlreadyExists ex) {
        log.error("Username already exists: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Username already exists");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
