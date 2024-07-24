package com.finance.bharat.dto.credit;

import lombok.Data;


@Data
public class CreditCredential {

    private String accountNumber;
    private  String password;
    private String bankPinCode;
    private String ifscCode;
    private String bankName;
    private double creditYourMoney;

}
