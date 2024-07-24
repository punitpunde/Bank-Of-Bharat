package com.finance.bharat.dto.upiPay;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyToUPIFromAccountRequest {

    private String upiId;
    private String accountNumber;
    private double payMoney;
}
