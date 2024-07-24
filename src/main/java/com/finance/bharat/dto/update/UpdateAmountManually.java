package com.finance.bharat.dto.update;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAmountManually {

    private String accountNumber;
    private double accountBalance;
}
