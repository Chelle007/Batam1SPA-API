package com.example.batam1spa.staff.advice;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.order.advice.OrderAdvice;
import com.example.batam1spa.staff.exception.StaffExceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.batam1spa.staff")
public class StaffAdvice {
    private static final Logger log = LoggerFactory.getLogger(OrderAdvice.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid JSON format: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StaffExceptions.TimeSlotNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleTimeSlotNotFoundException(StaffExceptions.TimeSlotNotFound ex) {
        log.error("TimeSlot Not Found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "TimeSlot Not Found: Invalid TimeSlotId");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StaffExceptions.InvalidTimeSlot.class)
    public ResponseEntity<BaseResponse<String>> handleInvalidTimeSlotException(StaffExceptions.InvalidTimeSlot ex) {
        log.error("Invalid TimeSlot: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid TimeSlot: Start time must be before End time.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StaffExceptions.DuplicateStaffName.class)
    public ResponseEntity<BaseResponse<String>> handleDuplicateStaffNameException(StaffExceptions.DuplicateStaffName ex) {
        log.error("Invalid TimeSlot: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid TimeSlot: Start time must be before End time.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StaffExceptions.StaffNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleStaffIdNotFoundException(StaffExceptions.StaffNotFound ex) {
        log.error("Invalid Staff: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Staff Not Found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGenericException(Exception ex) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}