package com.finance.bharat.exceptions;

public class InSufficientBalance extends RuntimeException{

    public InSufficientBalance(String message){
        super(message);
    }
}
