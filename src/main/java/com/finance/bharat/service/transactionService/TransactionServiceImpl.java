package com.finance.bharat.service.transactionService;

import com.finance.bharat.dto.transaction.TransactionRequest;
import com.finance.bharat.dto.transaction.TransactionResponse;
import com.finance.bharat.entity.TransactionDetailsHistory;
import com.finance.bharat.mapper.TransactionToTransactionResponse;
import com.finance.bharat.repository.TransactionHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Override
    public long countTransactionsByAccountNumberAndTimestamp(String accountNumber, LocalDateTime twentyFourHoursAgo, LocalDateTime currentDateTime) {
        return transactionHistoryRepository.countByAccountNumberAndLocalDateTimeBetween(accountNumber, twentyFourHoursAgo, currentDateTime);
    }

    @Override
    public void saveTransaction(TransactionRequest transactionRequest) {
        TransactionDetailsHistory transactionDetailsHistory = new TransactionDetailsHistory();
        transactionDetailsHistory.setTransactionId(transactionRequest.getTransactionId());
        transactionDetailsHistory.setAccountNumber(transactionRequest.getAccountNumber());
        transactionDetailsHistory.setLocalDateTime(transactionRequest.getLocalDateTime());
        transactionDetailsHistory.setIfscCode(transactionRequest.getIfscCode());
        transactionDetailsHistory.setDebitOrCreditMoney(transactionRequest.getDebitOrCreditMoney());
        transactionDetailsHistory.setDebitedOrCredited(transactionRequest.getDebitedOrCredited());

        transactionHistoryRepository.save(transactionDetailsHistory);
    }

    @Override
    public List<TransactionResponse> getAllTransaction(String accountNumber) {
        List<TransactionDetailsHistory> transactionDetailsHistories = transactionHistoryRepository.findAllByAccountNumberAndIfscCode(accountNumber);

        return transactionDetailsHistories.stream()
                .map(TransactionToTransactionResponse::transactionToResponse)
                .collect(Collectors.toList());
    }
}
