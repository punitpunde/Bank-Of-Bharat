package com.finance.bharat.service.transactionService;

import com.finance.bharat.dto.transaction.TransactionRequest;
import com.finance.bharat.dto.transaction.TransactionResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionService {
    long countTransactionsByAccountNumberAndTimestamp(String accountNumber, LocalDateTime twentyFourHoursAgo, LocalDateTime currentDateTime);
    void saveTransaction(TransactionRequest transactionRequest);
    List<TransactionResponse>getAllTransaction(String accountNumber);
}
