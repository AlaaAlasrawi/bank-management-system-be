package com.bank.backend.domain.services;


import com.bank.backend.domain.model.Transaction;
import com.bank.backend.persistance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public Page<Transaction> getAllTransactions(int page, int size, String sortBy, String sortDirection) {
        return transactionRepository.getAllTransactions(page,size,sortBy,sortDirection);
    }
}
