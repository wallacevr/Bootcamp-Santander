package com.project.bootcamp.santander.Exceptions;

public class BusinessException  extends RuntimeException{
    public BusinessException(String message){
        super(message);
    }
}
