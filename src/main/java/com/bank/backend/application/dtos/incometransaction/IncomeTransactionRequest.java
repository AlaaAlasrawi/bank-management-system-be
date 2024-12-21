package com.bank.backend.application.dtos.incometransaction;

import com.bank.backend.domain.enums.IncomeMethods;
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
public class IncomeTransactionRequest { // 6
    private BigDecimal amount;

    private IncomeMethods incomeMethods;

    private String description;

    private String currency;

    private String reference;

    private Long bankAccountId;
}
