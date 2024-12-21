package com.bank.backend.persistance.jpa;

import com.bank.backend.domain.enums.TransactionStatus;
import com.bank.backend.domain.model.Transaction;
import com.bank.backend.persistance.entity.OutcomeTransactionEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OutcomeTransactionJpaRepository extends JpaRepository<OutcomeTransactionEntity,Long> {
    @Transactional
    @Modifying
    @Query("UPDATE outcome_transaction it SET it.status = :transactionStatus, it.updatedAt = :currentTime WHERE it.id = :id")
    void updateStatus(@Param("id") Long id,
                      @Param("currentTime") LocalDateTime currentTime,
                      @Param("transactionStatus") TransactionStatus transactionStatus);



    @Query(value = """
        SELECT 
            it.amount AS amount,
            it.description AS description,
            it.transaction_date AS transactionDate,
            'income' AS type
        FROM income_transaction it
        UNION ALL
        SELECT 
            ot.amount AS amount,
            ot.description AS description,
            ot.transaction_date AS transactionDate,
            'outcome' AS type
        FROM outcome_transaction ot
        ORDER BY transactionDate DESC
        """,
            countQuery = """
        SELECT COUNT(*) FROM (
            SELECT it.id FROM income_transaction it
            UNION ALL
            SELECT ot.id FROM outcome_transaction ot
        ) AS transactions
        """,
            nativeQuery = true)
    Page<Object[]> findAllTransactions(Pageable pageable);
}
