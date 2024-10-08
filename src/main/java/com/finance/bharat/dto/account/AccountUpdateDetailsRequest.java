package com.finance.bharat.dto.account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateDetailsRequest {

    private String accountHolderName;

    private String accountNumber;
    private String IfscCode;
    private String contactEmail;

    private String contactPhone;
    private String contactAddress;
    private String stateOfOrigin;
    private String pinCodeNumber;
    private String currentLocation;
    private String designation;
    private String country;
    private String accountType;
    private LocalDateTime localDateTime;
    private LocalDate accountOpenDate;

}
