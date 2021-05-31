package com.project.bootcamp.santander.Exceptions;

public class ExceptionResponse {
    private String message;
    ExceptionResponse(String message){
        this.message =  message;
    }
    public String getMessage() {
        return message;
    }

}
