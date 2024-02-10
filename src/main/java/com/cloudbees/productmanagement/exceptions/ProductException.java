package com.cloudbees.productmanagement.exceptions;

import com.cloudbees.productmanagement.enums.ErrorCode;

public class ProductException extends RuntimeException{
    private final  int httpStatusCode;
    private final String  message;
    public ProductException(ErrorCode errorCode, String message) {
        this.httpStatusCode = errorCode.getResponseCode();
        this.message = message;
    }
    public int getHttpStatusCode(){
        return httpStatusCode;
    }
    public String getMessage(){
        return message;
    }

}
