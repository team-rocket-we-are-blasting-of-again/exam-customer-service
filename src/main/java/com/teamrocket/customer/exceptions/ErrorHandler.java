package com.teamrocket.customer.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<ExceptionDTO> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
        ExceptionDTO errorDTO = new ExceptionDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), resourceNotFoundException.getMessage());
        return new ResponseEntity<ExceptionDTO>(
                errorDTO,
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ExceptionDTO> globalExceptionHandler(ResourceNotFoundException resourceNotFoundException) {
        ExceptionDTO errorDTO = new ExceptionDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), resourceNotFoundException.getMessage());
        return new ResponseEntity<ExceptionDTO>(
                errorDTO,
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}
