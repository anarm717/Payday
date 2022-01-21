package com.payday.bank.exception;

/**
 * @author Anar
 */
public class UserNotFoundException extends GeneralException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}