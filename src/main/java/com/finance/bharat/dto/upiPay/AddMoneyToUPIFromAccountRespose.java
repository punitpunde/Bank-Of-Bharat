package com.finance.bharat.dto.upiPay;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMoneyToUPIFromAccountRespose {

    private String responseMessage;
    private String status;

}
