package com.payday.bank.exception;

import java.util.Map;


public class ValidationException extends RuntimeException {
    private final Map<String, String> errors;

    public ValidationException(Map<String, String> message) {
        errors = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
