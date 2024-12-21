package com.bank.backend.persistance.repository;

import com.bank.backend.domain.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository {
    Page<Transaction> getAllTransactions(int page, int size, String sortBy, String sortDirection);
}
