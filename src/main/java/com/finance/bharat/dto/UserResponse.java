package com.finance.bharat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String accountId;
    private String message;
    private String accountHolderName;
    private String accountNumber;
    private String bankName;
    private String bankBranch;
    private String IfscCode;
    private LocalDate accountOpenDate;
    private String isHaveUpiId;
    private double accountBalance;
    private String routingNumber;
    private String password;
    private String accountType;
    private String contactEmail;
    private String contactPhone;
}

