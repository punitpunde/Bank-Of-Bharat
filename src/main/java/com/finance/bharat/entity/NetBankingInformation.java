package com.finance.bharat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "NET_BANKING")
public class NetBankingInformation {

   @Id
   private String netId;
   private String accountHolderName;
   private String accountNumber;
   private String ifscCode;
   private String password;
   private String NET_BANKING_ID;
   private LocalDateTime localDateTime;

}
