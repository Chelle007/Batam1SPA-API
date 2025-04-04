package com.example.batam1spa.common.advice;

import com.example.batam1spa.common.dto.BaseResponse;
import com.example.batam1spa.common.exception.CommonExceptions;
import com.example.batam1spa.order.advice.OrderAdvice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.batam1spa.common")
public class CommonAdvice {
    private static final Logger log = LoggerFactory.getLogger(OrderAdvice.class);

    @ExceptionHandler(CommonExceptions.InvalidNumber.class)
    public ResponseEntity<BaseResponse<String>> handleInvalidNumberException(CommonExceptions.InvalidNumber ex) {
        log.error("Invalid number: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid number");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CommonExceptions.InvalidDate.class)
    public ResponseEntity<BaseResponse<String>> handleInvalidDateException(CommonExceptions.InvalidDate ex) {
        log.error("Invalid date: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid date");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
