package com.finance.bharat.dto.transferMoney;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferMoneyRequest {

        private String fullNameOfRecipient;
        private String accountNumberOfRecipient;
        private String accountNumberOfSender;
        private double transferAmount;
        private LocalDate localDate;

}
