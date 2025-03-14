package com.example.batam1spa.user.advice;

import com.example.batam1spa.user.exception.UserExceptions;
import com.example.batam1spa.common.dto.BaseResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.batam1spa.user")
public class UserAdvice {
    private static final Logger log = LoggerFactory.getLogger(UserAdvice.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid JSON format: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGenericException(Exception ex) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
