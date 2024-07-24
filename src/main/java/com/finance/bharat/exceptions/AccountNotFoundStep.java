package com.finance.bharat.exceptions;

public class AccountNotFoundStep extends RuntimeException{
    public AccountNotFoundStep(String message){
        super(message);
    }
}
