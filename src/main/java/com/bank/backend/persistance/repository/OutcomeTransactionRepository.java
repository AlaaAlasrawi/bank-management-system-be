package com.bank.backend.persistance.repository;

import com.bank.backend.domain.enums.TransactionStatus;
import com.bank.backend.domain.model.OutcomeTransaction;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeTransactionRepository {
    OutcomeTransaction save(OutcomeTransaction outcomeTransaction);

    OutcomeTransaction getOutcomeTransactionById(Long id);

    Page<OutcomeTransaction> getAllOutcomeTransactions(int page, int size, String sortBy, String sortDirection);

    void updateStatus(Long id, TransactionStatus transactionStatus);
}
