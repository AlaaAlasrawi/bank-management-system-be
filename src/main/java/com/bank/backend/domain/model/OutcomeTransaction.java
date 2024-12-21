package com.bank.backend.domain.model;

import com.bank.backend.domain.enums.OutcomeMethods;
import com.bank.backend.domain.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutcomeTransaction {
    private Long id;

    private BigDecimal amount;

    private OutcomeMethods outcomeMethods;

    private String description;

    private String currency;

    private String reference;

    private TransactionStatus status;

    private LocalDateTime transactionDate;

    private LocalDateTime updatedAt;

    private Long bankAccountId;

    private Long sourceBankAccountId;
}
