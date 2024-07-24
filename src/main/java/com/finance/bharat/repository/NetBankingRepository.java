package com.finance.bharat.repository;

import com.finance.bharat.entity.NetBankingInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NetBankingRepository extends JpaRepository<NetBankingInformation,String> {

    @Query("SELECT u FROM NetBankingInformation u WHERE u.accountNumber = :accountNumber AND u.ifscCode = :ifscCode")
    NetBankingInformation findByAccountNumberAndIfscCode(String accountNumber,String ifscCode);
}
