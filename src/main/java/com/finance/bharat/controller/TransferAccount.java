package com.finance.bharat.controller;

import com.finance.bharat.dto.transferMoney.TransferMoneyRequest;
import com.finance.bharat.dto.transferMoney.TransferMoneyResponse;
import com.finance.bharat.service.upiAndNetBankingService.UPIAndNetBankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("finance/bharat/transfer")
public class TransferAccount {

    @Autowired
    private UPIAndNetBankingService upiAndNetBankingService;

    @PostMapping("/process")
    ResponseEntity<TransferMoneyResponse> transactionGive(@RequestBody TransferMoneyRequest transferMoneyRequest){
        TransferMoneyResponse response = upiAndNetBankingService.moneyTransferAccountToAccount(transferMoneyRequest);
        return new ResponseEntity<TransferMoneyResponse>(response , HttpStatus.ACCEPTED);
    }
}
