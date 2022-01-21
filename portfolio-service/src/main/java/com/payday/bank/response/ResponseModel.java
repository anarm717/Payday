package com.payday.bank.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
//    private int code;
    private String message;

    ResponseModel(String message){
        this.message = message;
    }

}
