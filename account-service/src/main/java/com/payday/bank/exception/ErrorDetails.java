package com.payday.bank.exception;

import lombok.Data;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class ErrorDetails {

    private String message;
    public ErrorDetails(String message) {

        this.message = message;

    }

}