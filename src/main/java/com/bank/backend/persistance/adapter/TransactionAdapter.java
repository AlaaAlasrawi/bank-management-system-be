package com.bank.backend.persistance.adapter;

import com.bank.backend.domain.model.Transaction;
import com.bank.backend.persistance.jpa.OutcomeTransactionJpaRepository;
import com.bank.backend.persistance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionAdapter implements TransactionRepository {
    private final OutcomeTransactionJpaRepository outcomeTransactionJpaRepository;

    @Override
    public Page<Transaction> getAllTransactions(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        // Fetch raw results from the database
        Page<Object[]> rawResults = outcomeTransactionJpaRepository.findAllTransactions(pageable);

        // Map raw results to Transaction objects
        List<Transaction> transactions = rawResults.stream()
                .map(this::mapToTransaction) // Internal mapping function
                .toList();

        // Return paginated results
        return new PageImpl<>(transactions, pageable, rawResults.getTotalElements());
    }

    // Internal function to map Object[] to Transaction
    private Transaction mapToTransaction(Object[] record) {
        return Transaction.builder()
                .amount((BigDecimal) record[0])
                .description((String) record[1])
                .transactionDate(convertToLocalDateTime(record[2]))
                .type((String) record[3])
                .build();
    }

    // Helper method to convert java.sql.Timestamp to java.time.LocalDateTime
    private LocalDateTime convertToLocalDateTime(Object timestamp) {
        if (timestamp instanceof java.sql.Timestamp ts) {
            return ts.toLocalDateTime();
        }
        throw new IllegalArgumentException("Invalid type for timestamp: " + timestamp.getClass().getName());
    }
}
