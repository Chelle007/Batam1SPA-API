package com.example.batam1spa.leave.exception;

public class LeaveExceptions {
    private LeaveExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class InvalidPageNumber extends RuntimeException {
        public InvalidPageNumber(String message) {
            super(message);
        }
    }

    public static class InvalidPageSize extends RuntimeException {
        public InvalidPageSize(String message) {
            super(message);
        }
    }
}