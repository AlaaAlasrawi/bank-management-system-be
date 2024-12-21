package com.bank.backend.persistance.jpa;

import com.bank.backend.domain.enums.TransactionStatus;
import com.bank.backend.persistance.entity.IncomeTransactionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface IncomeTransactionJpaRepository extends JpaRepository<IncomeTransactionEntity,Long> {

    @Transactional
    @Modifying
    @Query("UPDATE income_transaction it SET it.status = :transactionStatus, it.updatedAt = :currentTime WHERE it.id = :id")
    void updateStatus(@Param("id") Long id,
                      @Param("currentTime") LocalDateTime currentTime,
                      @Param("transactionStatus") TransactionStatus transactionStatus);
}
