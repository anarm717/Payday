package com.payday.bank.exception;

/**
 * @author Anar
 **/
public class DataIntegrityException extends RuntimeException{

    public DataIntegrityException() {
        super();
    }

    public DataIntegrityException(String message) {
        super(message);
    }
}
