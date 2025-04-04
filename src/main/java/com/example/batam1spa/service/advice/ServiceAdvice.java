package com.example.batam1spa.service.advice;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.service.exception.ServiceExceptions;
import com.example.batam1spa.order.advice.OrderAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.batam1spa.service")
public class ServiceAdvice {
    private static final Logger log = LoggerFactory.getLogger(OrderAdvice.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid JSON format: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServiceExceptions.ServiceNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleServiceNotFoundException(ServiceExceptions.ServiceNotFound ex) {
        log.error("Service not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Service not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceExceptions.ServiceDescriptionNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleServiceDescriptionNotFoundException(ServiceExceptions.ServiceDescriptionNotFound ex) {
        log.error("Service description not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Service description not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ServiceExceptions.ServiceNameExisted.class)
    public ResponseEntity<BaseResponse<String>> handleServiceNameExistedException(ServiceExceptions.ServiceNameExisted ex) {
        log.error("Service name existed: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Service name existed");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGenericException(Exception ex) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
