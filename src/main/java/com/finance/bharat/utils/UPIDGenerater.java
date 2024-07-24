package com.finance.bharat.utils;

import java.util.Random;

public class UPIDGenerater {

    public String generateUPID(String fullName){
        if (fullName == null){
            return null;
        }

        String [] names = fullName.split(" ");
        if(names.length == 2){
            String firstName = names[0];
            String secondName = names[1];

            if(firstName.isEmpty() || secondName.isEmpty()){
                return null;
            }

            String upid = secondName+firstName+"@"+"bharat";
            return upid;
        }
        return null;
    }

    public String generatePin() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int upiCode = random.nextInt(max - min + 1) + min;
        return String.format("%06d", upiCode); // Corrected the formatting
    }

}
