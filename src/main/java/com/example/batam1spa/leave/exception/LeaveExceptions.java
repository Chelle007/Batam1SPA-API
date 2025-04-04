package com.example.batam1spa.leave.exception;

public class LeaveExceptions {
    private LeaveExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class LeaveNotFound extends RuntimeException {
        public LeaveNotFound(String message) {
            super(message);
        }
    }

    public static class StaffNotFound extends RuntimeException {
        public StaffNotFound(String message) {
            super(message);
        }
    }

}
