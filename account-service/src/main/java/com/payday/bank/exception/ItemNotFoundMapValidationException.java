package com.payday.bank.exception;

import java.util.Map;

/**
 * @author  Anar
 */
public class ItemNotFoundMapValidationException extends RuntimeException {

    private Map<String, String> errors;
//    public ItemNotFoundMapValidationException() {
//        super();
//    }
//
//    public ItemNotFoundMapValidationException(String message) {
//        super(message);
//    }
    public ItemNotFoundMapValidationException(Map<String, String> message) {

        errors = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
