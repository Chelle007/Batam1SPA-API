package com.example.batam1spa.customer.exception;

public class CustomerExceptions {

    private CustomerExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class CustomerNotFound extends RuntimeException {
        public CustomerNotFound(String message) {
            super(message);
        }
    }

}
