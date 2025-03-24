package com.example.batam1spa.order.advice;

import com.example.batam1spa.order.exception.OrderExceptions;
import com.example.batam1spa.common.dto.BaseResponse;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "com.example.batam1spa.order")
public class OrderAdvice {
    private static final Logger log = LoggerFactory.getLogger(OrderAdvice.class);

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid JSON format: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid JSON format");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderExceptions.OrderNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleOrderNotFoundException(OrderExceptions.OrderNotFound ex) {
        log.error("Order not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Order not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderExceptions.ServiceNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleServiceNotFoundException(OrderExceptions.ServiceNotFound ex) {
        log.error("Service not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Service not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderExceptions.TimeSlotNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleTimeSlotNotFoundException(OrderExceptions.TimeSlotNotFound ex) {
        log.error("Time slot not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Time slot not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderExceptions.AvailabilityNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleAvailabilityNotFoundException(OrderExceptions.AvailabilityNotFound ex) {
        log.error("Availability not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Availability not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderExceptions.CartItemNotFoundException.class)
    public ResponseEntity<BaseResponse<String>> handleCartItemNotFoundException(OrderExceptions.CartItemNotFoundException ex) {
        log.error("Cart item not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Cart item not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderExceptions.ServicePriceNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleServicePriceNotFoundException(OrderExceptions.ServicePriceNotFound ex) {
        log.error("Service price not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Service price not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderExceptions.OrderDetailNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleOrderDetailNotFoundException(OrderExceptions.OrderDetailNotFound ex) {
        log.error("Order Detail not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Order Detail not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderExceptions.StaffNotFound.class)
    public ResponseEntity<BaseResponse<String>> handleStaffNotFoundException(OrderExceptions.StaffNotFound ex) {
        log.error("Staff not found: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.NOT_FOUND, "Staff not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderExceptions.InvalidServiceSchedule.class)
    public ResponseEntity<BaseResponse<String>> handleInvalidServiceScheduleException(OrderExceptions.InvalidServiceSchedule ex) {
        log.error("Invalid service schedule: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid service schedule");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderExceptions.CartEmptyException.class)
    public ResponseEntity<BaseResponse<String>> handleCartEmptyExceptionException(OrderExceptions.CartEmptyException ex) {
        log.error("Cart Empty: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Cart Empty");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderExceptions.InvalidPhoneNumber.class)
    public ResponseEntity<BaseResponse<String>> handleInvalidPhoneNumberException(OrderExceptions.InvalidPhoneNumber ex) {
        log.error("Invalid phone number: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid phone number");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderExceptions.ServiceFullyBooked.class)
    public ResponseEntity<BaseResponse<String>> handleServiceFullyBookedException(OrderExceptions.ServiceFullyBooked ex) {
        log.error("Service fully booked: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Service fully booked");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderExceptions.InvalidQty.class)
    public ResponseEntity<BaseResponse<String>> handleInvalidQtyException(OrderExceptions.InvalidQty ex) {
        log.error("Invalid qty: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Invalid qty");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OrderExceptions.QtyMoreThanFour.class)
    public ResponseEntity<BaseResponse<String>> handleQtyMoreThanFourException(OrderExceptions.QtyMoreThanFour ex) {
        log.error("Qty more than 4: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.BAD_REQUEST, "Qty more than 4");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> handleGenericException(Exception ex) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);
        BaseResponse<String> response = BaseResponse.failure(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
