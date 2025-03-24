package com.example.batam1spa.availability.advice;

import com.example.batam1spa.availability.exception.AvailabilityExceptions;
import com.example.batam1spa.common.dto.BaseResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.batam1spa.availability")
public class AvailabilityAdvice {
    private static final Logger log = LoggerFactory.getLogger(AvailabilityAdvice.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid JSON format: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

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

    @ExceptionHandler(AvailabilityExceptions.BundleNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleBundleNotFoundException(AvailabilityExceptions.BundleNotFound ex) {
        log.error("Bundle not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Bundle not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AvailabilityExceptions.InvalidPax.class)
    public ResponseEntity<BaseResponse<String>> handleInvalidPaxException(AvailabilityExceptions.InvalidPax ex) {
        log.error("Invalid pax: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid pax");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AvailabilityExceptions.InvalidDuration.class)
    public ResponseEntity<BaseResponse<String>> handleInvalidDurationException(AvailabilityExceptions.InvalidDuration ex) {
        log.error("Invalid duration: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid duration");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGenericException(Exception ex) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
