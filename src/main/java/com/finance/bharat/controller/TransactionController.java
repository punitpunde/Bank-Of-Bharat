package com.finance.bharat.controller;

import com.finance.bharat.dto.transaction.TransactionResponse;
import com.finance.bharat.service.transactionService.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("finance/bharat/transaction/fetch")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction-enquiry/{accountNumber}")
    ResponseEntity<List<TransactionResponse>> getAllTransactions(@PathVariable("accountNumber") String accountNumber){
        List<TransactionResponse> transactionResponses = transactionService.getAllTransaction(accountNumber);
        return new ResponseEntity<>(transactionResponses, HttpStatus.ACCEPTED);
    }

}
