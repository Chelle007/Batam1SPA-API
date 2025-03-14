package com.example.batam1spa.availability.advice;

import com.example.batam1spa.availability.exception.AvailabilityExceptions;
import com.example.batam1spa.common.dto.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.batam1spa.availability")
public class AvailabilityAdvice {
    private static final Logger log = LoggerFactory.getLogger(AvailabilityAdvice.class);

    @ExceptionHandler(AvailabilityExceptions.AvailabilityNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleAvailabilityNotFoundException(AvailabilityExceptions.AvailabilityNotFound ex) {
        log.error("Availability not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Availability not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AvailabilityExceptions.OrderNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleOrderNotFoundException(AvailabilityExceptions.OrderNotFound ex) {
        log.error("Order not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Order not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AvailabilityExceptions.TimeSlotNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleTimeSlotNotFoundException(AvailabilityExceptions.TimeSlotNotFound ex) {
        log.error("Time slot not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Time slot not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
