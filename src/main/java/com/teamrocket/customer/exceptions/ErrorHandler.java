package com.teamrocket.customer.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Error> resourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException,
            HttpServletRequest request) {

        log.error("validation exception : " +
                resourceNotFoundException.getLocalizedMessage() +
                " for " +
                request.getRequestURI());

        return new ResponseEntity<>(
                Error.builder()
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("Request is not valid")
                        .timestamp(new Date())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Error> genericException(
            Exception exception,
            HttpServletRequest request) {

        log.error("exception : " +
                exception.getLocalizedMessage() +
                " for " +
                request.getRequestURI());

        return new ResponseEntity<>(
                Error.builder()
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("Could not process request")
                        .timestamp(new Date())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
