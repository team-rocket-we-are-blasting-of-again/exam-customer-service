package com.teamrocket.customer.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<ExceptionDTO> handleAccessDeniedException(CustomerNotFoundException customerNotFoundException) {
        ExceptionDTO errorDTO = new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), customerNotFoundException.getMessage());
        return new ResponseEntity<ExceptionDTO>(
                errorDTO,
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
