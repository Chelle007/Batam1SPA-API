package com.example.batam1spa.bundle.exception;

public class BundleExceptions {

    private BundleExceptions() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static class BundleNotFound extends RuntimeException {
        public BundleNotFound(String message) {
            super(message);
        }
    }

    public static class ServicePriceNotFound extends RuntimeException {
        public ServicePriceNotFound(String message) {
            super(message);
        }
    }

    public static class BundleDescriptionNotFound extends RuntimeException {
        public BundleDescriptionNotFound(String message) {
            super(message);
        }
    }
}
