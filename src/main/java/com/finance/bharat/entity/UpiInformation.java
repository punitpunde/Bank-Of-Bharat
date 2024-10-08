package com.finance.bharat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "UPI_INFO")
public class UpiInformation {

    @Id
    private String globalId;
    private String accountNumber;
    private String upiId;
    private String ifscCode;
    private String bankPassword;
    private String contactNumber;
    private String contactEmail;
    private String UPI_CODE;
    private double UPI_BALANCE;
    private String responseMessage;
}
