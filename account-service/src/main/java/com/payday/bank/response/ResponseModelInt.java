package com.payday.bank.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ResponseModelInt {
//    private int code;
    private int data;

    ResponseModelInt(int data){
        this.data = data;
    }

}
