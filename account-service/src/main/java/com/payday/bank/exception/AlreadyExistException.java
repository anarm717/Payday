package com.payday.bank.exception;

public class AlreadyExistException extends GeneralException {

    public AlreadyExistException() {
        super();
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
