package com.bank.backend.application.dtos.outcometransaction;

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
public class OutcomeTransactionRequest {
    private BigDecimal amount;

    private OutcomeMethods outcomeMethods;

    private String description;

    private String currency;

    private String reference;

    private Long bankAccountId;
}
