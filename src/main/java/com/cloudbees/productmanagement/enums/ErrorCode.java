package com.cloudbees.productmanagement.enums;

public enum ErrorCode {
    NOT_FOUND(404),
    CREATION_ERROR(400),
    UPDATION_ERROR(400);
    int responseCode;
    private ErrorCode(int code){
        this.responseCode = code;
    }
    public int getResponseCode(){
        return responseCode;
    }
}
