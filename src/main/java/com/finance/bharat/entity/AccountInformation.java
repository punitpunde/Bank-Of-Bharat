package com.finance.bharat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "USER_INFO")
public class AccountInformation {
    @Id
    private String accountId;
    private String accountHolderName;
    private String contactEmail;
    private String contactPhone;
    private String gender;
    private String contactAddress;
    private String stateOfOrigin;
    private String pinCodeNumber;
    private String currentLocation;
    private String designation;
    private String country;
    private String accountNumber;
    private String password;
    private String ifscCode;
    private String bankName;
    private String bankBranch;
    private String routingNumber;
    private String bankPinCode;
    private String accountType;
    private String isHaveUpiId;
    private double accountBalance;
    private String status;
    private LocalDateTime localDateTime;
    private LocalDate accountOpenData;
}
