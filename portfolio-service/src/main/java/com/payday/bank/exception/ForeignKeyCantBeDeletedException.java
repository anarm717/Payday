package com.payday.bank.exception;

/**
 * @author  Anar
 */
public class ForeignKeyCantBeDeletedException extends GeneralException {

    public ForeignKeyCantBeDeletedException() {
        super();
    }

    public ForeignKeyCantBeDeletedException(String message) {
        super(message);
    }
}
