package com.bank.backend.persistance.repository;

import com.bank.backend.domain.enums.TransactionStatus;
import com.bank.backend.domain.model.IncomeTransaction;
import org.springframework.data.domain.Page;

public interface IncomeTransactionRepository {
    IncomeTransaction save(IncomeTransaction incomeTransaction);

    IncomeTransaction getIncomeTransactionById(Long id);

    Page<IncomeTransaction> getAllIncomeTransactions(int page, int size, String sortBy, String sortDirection);


    void updateStatus(Long id, TransactionStatus transactionStatus);
}
