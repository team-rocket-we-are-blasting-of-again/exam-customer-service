package com.teamrocket.customer.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
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
// TODO: REMOVE WHEN APPROVED FROM GROUP

//    @ExceptionHandler({ResourceNotFoundException.class})
//    public ResponseEntity<ExceptionDTO> handleResourceNotFoundException(ResourceNotFoundException resourceNotFoundException) {
//        ExceptionDTO errorDTO = new ExceptionDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), resourceNotFoundException.getMessage());
//
//        return new ResponseEntity<ExceptionDTO>(
//                errorDTO,
//                new HttpHeaders(),
//                HttpStatus.INTERNAL_SERVER_ERROR
//        );
//    }

    //    @ExceptionHandler({Exception.class})
//    public ResponseEntity<ExceptionDTO> globalExceptionHandler(ResourceNotFoundException resourceNotFoundException) {
//        ExceptionDTO errorDTO = new ExceptionDTO(new Date(), HttpStatus.INTERNAL_SERVER_ERROR.value(), resourceNotFoundException.getMessage());
//        return new ResponseEntity<ExceptionDTO>(
//                errorDTO,
//                new HttpHeaders(),
//                HttpStatus.INTERNAL_SERVER_ERROR
//        );
//    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<APIError> resourceNotFoundException(
            ResourceNotFoundException resourceNotFoundException,
            HttpServletRequest request) {

        log.error("validation exception : " +
                resourceNotFoundException.getLocalizedMessage() +
                " for " +
                request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .errorMessage(resourceNotFoundException.getLocalizedMessage())
                        .errorCode(HttpStatus.BAD_REQUEST.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("Request is not valid")
                        .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<APIError> genericException(
            Exception exception,
            HttpServletRequest request) {

        log.error("exception : " +
                exception.getLocalizedMessage() +
                " for " +
                request.getRequestURI());

        return new ResponseEntity<>(
                APIError.builder()
                        .errorMessage(exception.getLocalizedMessage())
                        .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.toString())
                        .request(request.getRequestURI())
                        .requestType(request.getMethod())
                        .customMessage("Could not process request")
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
