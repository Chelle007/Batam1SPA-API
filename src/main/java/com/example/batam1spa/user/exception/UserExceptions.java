package com.example.batam1spa.user.exception;

public class UserExceptions {

    private UserExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class UserNotFound extends RuntimeException {
        public UserNotFound(String message) {
            super(message);
        }
    }
}
