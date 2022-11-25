package com.teamrocket.customer.exceptions;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Builder
@Data
public class Error {

    //private String errorMessage;
    private String errorCode;
    private String request;
    private String requestType;
    private String customMessage;
    private Date timestamp;

}
