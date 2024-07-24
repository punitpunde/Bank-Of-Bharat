package com.finance.bharat.utils;

import java.util.Random;

public class AccountDetailsGenarators {

    public String generateAccountNumber(){
        Random random=new Random();
        long accountNumberLong=random.nextLong()%1000000000000L+1000000000000L;
        return String.valueOf(accountNumberLong);
    }

    public String generateIFSC(){
        Random random=new Random();
        int branchCodeInt=random.nextInt(9000)+1000;
        return String.valueOf(branchCodeInt);
    }

    public String generatePin(){
        Random random=new Random();
        long pin=random.nextLong()%1000000000000L+1000000000000L;
        return String.valueOf(pin);
    }

    public String generateBankCode(){
        Random random=new Random();
        long bankCodeInt=random.nextLong()%1000000000000L+1000000000000L;
        return String.valueOf(bankCodeInt);
    }

    public String generateBranchCode() {
        Random random = new Random();
        int branchCodeInt = random.nextInt(9000) + 1000;
        return String.valueOf(branchCodeInt);
    }

}
