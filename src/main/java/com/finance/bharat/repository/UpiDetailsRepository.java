package com.finance.bharat.repository;

import com.finance.bharat.entity.UpiInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UpiDetailsRepository extends JpaRepository<UpiInformation,String> {

    @Query("SELECT u FROM UpiInformation u WHERE u.upiId = :upiId")
    UpiInformation findByUpiId(@Param("upiId") String upiId);

    @Query("SELECT u FROM UpiInformation u WHERE u.accountNumber = :accountNumber AND u.bankPassword = :bankPassword")
    UpiInformation findByAccountNumberAndPassword(@Param("accountNumber") String accountNumber, @Param("bankPassword") String bankPassword);
}
