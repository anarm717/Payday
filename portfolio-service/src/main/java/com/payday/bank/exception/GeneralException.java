package com.payday.bank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author  Anar
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class GeneralException extends RuntimeException {

    public GeneralException() {
        super();
    }

    public GeneralException(String message) {
        super(message);
    }
}