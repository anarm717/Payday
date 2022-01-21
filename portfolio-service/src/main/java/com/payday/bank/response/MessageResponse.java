package com.payday.bank.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Anar
 */
public class MessageResponse {

    public static ResponseEntity<?> successResponse(String reason, Object o) {
        return ResponseEntity.ok().body(new ResponseModelDTO<>(reason, o));
    }

    public static ResponseEntity<?> successResponseInt(int reason) {
        return new ResponseEntity(new ResponseModelInt(reason),HttpStatus.OK);
    }

    public static ResponseEntity<?> successDelete(String reason) {
        return ResponseEntity.ok().body(new ResponseModelDTO<>(reason));
    }


    public static ResponseEntity<?> successMessage(String message) {
        return ResponseEntity.ok().body(new ResponseMessageDTO(message));
    }

    public static ResponseEntity<?> successDelete(String reason, Object o) {
        return ResponseEntity.ok().body(new ResponseModelDTO<>(reason,o));
    }

    public static ResponseEntity<?> failureResponse(String reason, Object o) {
        return new ResponseEntity(new ResponseModelDTO( reason), HttpStatus.NOT_FOUND);
//        return ResponseEntity.ok().body(new ResponseModelDTO<>(404, reason, o));
    }
    public static ResponseEntity<?> unprocessableResponse(String reason) {
        return new ResponseEntity(new ResponseModelDTO(reason),HttpStatus.UNPROCESSABLE_ENTITY);
    }
    public static ResponseEntity<?> errorResponse(String reason, Object o) {
        return new ResponseEntity(new ResponseModelDTO(reason),HttpStatus.INTERNAL_SERVER_ERROR);
//        return ResponseEntity.ok().body(new ResponseModelDTO<>(404, reason, o));
    }
    public static ResponseEntity<?> errorResponse(String reason ) {
        return new ResponseEntity(new ResponseModelDTO(reason),HttpStatus.INTERNAL_SERVER_ERROR);
//        return ResponseEntity.ok().body(new ResponseModelDTO<>(404, reason, o));
    }

    public static ResponseEntity<?> failureResponse(String reason) {
        return new ResponseEntity(new ResponseModel(reason),HttpStatus.NOT_FOUND);
    }
}