package com.payday.bank.exception;

/**
 * @author Anar
 */
public class NotEnoughQuantityException extends GeneralException {

    public NotEnoughQuantityException() {
        super();
    }

    public NotEnoughQuantityException(String message) {
        super(message);
    }
}
