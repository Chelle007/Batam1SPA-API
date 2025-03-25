package com.example.batam1spa.leave.advice;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.order.advice.OrderAdvice;
import com.example.batam1spa.leave.exception.LeaveExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.batam1spa.leave")
public class LeaveAdvice {
    private static final Logger log = LoggerFactory.getLogger(OrderAdvice.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid JSON format: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LeaveExceptions.InvalidPageNumber.class)
    public ResponseEntity<BaseResponse<String>> handleInvalidPageNumberException(LeaveExceptions.InvalidPageNumber ex) {
        log.error("Invalid page number: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid page number: page number is less than 0");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LeaveExceptions.InvalidPageSize.class)
    public ResponseEntity<BaseResponse<String>> handleInvalidPageSizeException(LeaveExceptions.InvalidPageSize ex) {
        log.error("Invalid page size: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid page size: page size is less than 1");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LeaveExceptions.StaffIdNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleStaffIdNotFoundException(LeaveExceptions.StaffIdNotFound ex) {
        log.error("Invalid StaffId: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "StaffId Not Found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LeaveExceptions.LeaveIdNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleLeaveIdNotFoundException(LeaveExceptions.LeaveIdNotFound ex) {
        log.error("Invalid LeaveId: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "LeaveId Not Found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGenericException(Exception ex) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}