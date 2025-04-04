package com.example.batam1spa.common.exception;

public class CommonExceptions {
    private CommonExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class InvalidNumber extends RuntimeException {
        public InvalidNumber(String message) {
            super(message);
        }
    }

    public static class InvalidDate extends RuntimeException {
        public InvalidDate(String message) {
            super(message);
        }
    }
}
