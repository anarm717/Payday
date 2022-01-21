package com.payday.bank.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class ResponseMessageDTO extends ResponseModel {
    public ResponseMessageDTO(String message) {
        super(message);
    }
}
