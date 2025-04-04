package com.example.batam1spa.bundle.advice;

import com.example.batam1spa.bundle.exception.BundleExceptions;
import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.order.advice.OrderAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.batam1spa.bundle")
public class BundleAdvice {
    private static final Logger log = LoggerFactory.getLogger(OrderAdvice.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid JSON format: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BundleExceptions.BundleNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleBundleNotFoundException(BundleExceptions.BundleNotFound ex) {
        log.error("Bundle not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Bundle not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BundleExceptions.ServicePriceNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleServicePriceNotFoundException(BundleExceptions.ServicePriceNotFound ex) {
        log.error("Service price not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Service price not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BundleExceptions.BundleDescriptionNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleBundleDescriptionNotFoundException(BundleExceptions.BundleDescriptionNotFound ex) {
        log.error("Bundle description not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Bundle description not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGenericException(Exception ex) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
