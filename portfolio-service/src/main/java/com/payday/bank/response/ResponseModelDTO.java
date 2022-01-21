package com.payday.bank.response;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class ResponseModelDTO<T> extends ResponseModel {
    private T data;
//
//    public ResponseModelDTO(int code, String message, T data) {
////        super(code, message);
//        super(message);
//        this.data = data;
//    }

    public ResponseModelDTO(String message, T data) {
        super(message);
        this.data = data;
    }

    public ResponseModelDTO(T data) {
        this.data = data;
    }
}
