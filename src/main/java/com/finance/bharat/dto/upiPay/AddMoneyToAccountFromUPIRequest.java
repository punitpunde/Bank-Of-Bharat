package com.finance.bharat.dto.upiPay;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyToAccountFromUPIRequest {

    private double addedFromUpi;
    private String accountNumber;
    private String password;
    private String upiId;
}
