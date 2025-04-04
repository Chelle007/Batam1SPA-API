package com.example.batam1spa.service.exception;

public class ServiceExceptions {
    private ServiceExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class ServiceNotFound extends RuntimeException {
        public ServiceNotFound(String message) {
            super(message);
        }
    }
}
