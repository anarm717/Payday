package com.payday.bank.exception;

public class CustomValidationException extends GeneralException{

    public CustomValidationException() {
        super();
    }

    public CustomValidationException(String message) {
        super(message);
    }
}
