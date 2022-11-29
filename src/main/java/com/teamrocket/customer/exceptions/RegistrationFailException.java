package com.teamrocket.customer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
public class RegistrationFailException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RegistrationFailException(String message) {
        super(message);
    }

}
