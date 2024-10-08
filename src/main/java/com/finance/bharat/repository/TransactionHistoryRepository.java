package com.finance.bharat.repository;

import com.finance.bharat.entity.TransactionDetailsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionHistoryRepository extends JpaRepository<TransactionDetailsHistory, String> {

    long countByAccountNumberAndLocalDateTimeBetween(String accountNumber,
                                                     LocalDateTime startTimestamp,
                                                     LocalDateTime endTimestamp
    );

    @Query("SELECT u FROM TransactionDetailsHistory u WHERE u.accountNumber = :accountNumber")
    List<TransactionDetailsHistory> findAllByAccountNumberAndIfscCode(String accountNumber);

}
