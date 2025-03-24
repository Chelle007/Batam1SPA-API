package com.example.batam1spa.availability.exception;

public class AvailabilityExceptions {

    private AvailabilityExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class AvailabilityNotFound extends RuntimeException {
        public AvailabilityNotFound(String message) {
            super(message);
        }
    }

    public static class OrderNotFound extends RuntimeException {
        public OrderNotFound(String message) {
            super(message);
        }
    }

    public static class TimeSlotNotFound extends RuntimeException {
        public TimeSlotNotFound(String message) {
            super(message);
        }
    }

    public static class BundleNotFound extends RuntimeException {
        public BundleNotFound(String message) {
            super(message);
        }
    }

    public static class InvalidPax extends RuntimeException {
        public InvalidPax(String message) {
            super(message);
        }
    }

    public static class InvalidDuration extends RuntimeException {
        public InvalidDuration(String message) {
            super(message);
        }
    }
}
