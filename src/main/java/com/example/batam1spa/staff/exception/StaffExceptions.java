package com.example.batam1spa.staff.exception;

public class StaffExceptions {
    private StaffExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class TimeSlotNotFound extends RuntimeException {
        public TimeSlotNotFound(String message) {
            super(message);
        }
    }

    public static class InvalidTimeSlot extends RuntimeException {
        public InvalidTimeSlot(String message) {
            super(message);
        }
    }

    public static class DuplicateStaffName extends RuntimeException {
        public DuplicateStaffName(String message) {
            super(message);
        }
    }

    public static class StaffNotFound extends RuntimeException {
        public StaffNotFound(String message) {
            super(message);
        }
    }
}