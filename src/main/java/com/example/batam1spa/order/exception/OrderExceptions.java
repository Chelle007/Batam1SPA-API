package com.example.batam1spa.order.exception;

public class OrderExceptions {

    private OrderExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class OrderNotFound extends RuntimeException {
        public OrderNotFound(String message) {
            super(message);
        }
    }

    public static class ServiceNotFound extends RuntimeException {
        public ServiceNotFound(String message) {
            super(message);
        }
    }

    public static class TimeSlotNotFound extends RuntimeException {
        public TimeSlotNotFound(String message) {
            super(message);
        }
    }

    public static class InvalidServiceSchedule extends RuntimeException {
        public InvalidServiceSchedule(String message) {
            super(message);
        }
    }

    public static class AvailabilityNotFound extends RuntimeException {
        public AvailabilityNotFound(String message) {
            super(message);
        }
    }

    public static class CartEmptyException extends RuntimeException {
        public CartEmptyException(String message) {
            super(message);
        }
    }

    public static class CartItemNotFoundException extends RuntimeException {
        public CartItemNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidPhoneNumber extends RuntimeException {
        public InvalidPhoneNumber(String message) {
            super(message);
        }
    }
}
