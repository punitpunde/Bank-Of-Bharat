package com.finance.bharat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRANSACTION_HISTORY")
@Builder
public class TransactionDetailsHistory {

    @Id
    private String transactionId;
    private String accountNumber;
    private String ifscCode;
    private String debitedOrCredited;
    private LocalDateTime localDateTime;
    private double debitOrCreditMoney;
}
