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
}
