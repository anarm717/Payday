package com.payday.bank.exception;

import com.payday.bank.response.Reason;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.*;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

            Map<String, String> errors = new HashMap<>();
            ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiErrorDetails errorDetails = new ApiErrorDetails("Validasiya xətası", errors);

        return new ResponseEntity(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Set<String>> handleConstraintViolation(ConstraintViolationException e) {

        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        Map<String, String> errors = new LinkedHashMap<>();

        constraintViolations.forEach((error) -> {
            String fieldName = "";
            for (Path.Node node : error.getPropertyPath()) {
                fieldName = ((node.getIndex() == null) ? "" : "[" + node.getIndex()+ "].") + node.getName();
            }
//            System.out.println(fieldName);
//            String fieldName = error.getPropertyPath().toString().substring(23);
            String errorMessage = error.getMessage();
            errors.put(fieldName, errorMessage);
        });

        ApiErrorDetails errorDetails = new ApiErrorDetails("Validasiya xətası", errors);

        return new ResponseEntity(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);

    }

    @ExceptionHandler(GeneralException.class)
    public final ResponseEntity<ErrorDetails> handleGeneralException(GeneralException ex, WebRequest request) {
        System.out.println("GeneralException");
        ErrorDetails errorDetails =
                new ErrorDetails(
                        Reason.UNKNOW.getValue());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(CanNotNullException.class)
    public final ResponseEntity<ErrorDetails> handleCanNotNullException(CanNotNullException ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(
//                        new Date(),
//                        4005,
                        Reason.NOT_FOUND.getValue());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(
//                        new Date(),
//                        404,
                        ex.getMessage() );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ItemNotFoundException.class)
    public final ResponseEntity<ErrorDetails> handleItemNotFoundException(ItemNotFoundException ex, WebRequest request) {
        ErrorDetails  errorDetails =
                new ErrorDetails(
//                        new Date(),
//                        404,
                        Reason.NOT_FOUND.getValue()
                );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughException.class)
    public final ResponseEntity<ErrorDetails> handleItemNotEnoughException(NotEnoughException ex, WebRequest request) {
        ErrorDetails  errorDetails =
                new ErrorDetails(
                        Reason.NOT_ENOUGH_AMOUNT.getValue()
                );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotEnoughQuantityException.class)
    public final ResponseEntity<ErrorDetails> handleItemNotEnoughQuantityException(NotEnoughQuantityException ex, WebRequest request) {
        ErrorDetails  errorDetails =
                new ErrorDetails(
                        Reason.NOT_ENOUGH_QUANTITY.getValue()
                );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NameAlreadyExistException.class)
    public final ResponseEntity<ErrorDetails> handleNameAlreadyExistException(NameAlreadyExistException ex, WebRequest request) {
        ErrorDetails errorDetails =
                new ErrorDetails(
//                        new Date(),
//                        403,
                        Reason.ALREADY_EXIST.getValue());
        return new ResponseEntity<>(errorDetails, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(ForeignKeyCantBeDeletedException.class)
    public final ResponseEntity<ErrorDetails> handleForeignKeyCantBeDeletedException(ForeignKeyCantBeDeletedException ex, WebRequest request) {
        System.out.println("ForeignKeyCantBeDeletedException");
        ErrorDetails errorDetails =
                new ErrorDetails(
//                        new Date(),
//                        404,
                        Reason.CONSTRAINT_VIOLATED.getValue());
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomValidationException.class)
    public final ResponseEntity<ErrorDetails> handleBadRequestException(CustomValidationException ex, WebRequest request) {
        System.out.println("BadRequestException");
        ErrorDetails errorDetails =
                new ErrorDetails(
                        ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler(SmbFaylException.class)
//    public final ResponseEntity<ErrorDetails> handleSmbException(SmbFaylException ex, WebRequest request) {
//        System.out.println("SMBEXCEPTION OCCURED");
//        ErrorDetails errorDetails =
//                new ErrorDetails(
//                        ex.getMessage());
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @ExceptionHandler(NoSuchElementException.class)
    public final ResponseEntity<ErrorDetails> handleNoSuchElementException(NoSuchElementException ex, WebRequest request) {
        ErrorDetails  errorDetails =
                new ErrorDetails(
                        Reason.NOT_FOUND.getValue()
                );

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(InternalErrorException.class)
//    public final ResponseEntity<ErrorDetails> handleGeneralException(InternalErrorException ex, WebRequest request) {
//        System.out.println("InternalErrorException");
//        ErrorDetails errorDetails = new ErrorDetails(Reason.UNKNOW.getValue());
//        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
}