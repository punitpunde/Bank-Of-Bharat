package com.finance.bharat.exceptions;

public class EmailAlreadyExistStep extends RuntimeException{
    public EmailAlreadyExistStep(String message){
        super(message);
    }
}
