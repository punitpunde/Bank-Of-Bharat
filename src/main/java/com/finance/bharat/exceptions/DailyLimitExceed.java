package com.finance.bharat.exceptions;

public class DailyLimitExceed extends RuntimeException{

    public DailyLimitExceed(String message){
        super(message);
    }
}
