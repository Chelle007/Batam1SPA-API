package com.example.batam1spa.staff.exception;

public class StaffExceptions {
    private StaffExceptions() {
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