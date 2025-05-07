package com.testing.app.exception;

public class CustomerEmailUnavailableException extends RuntimeException {
    public CustomerEmailUnavailableException(String message) {
        super(message);
    }
}
