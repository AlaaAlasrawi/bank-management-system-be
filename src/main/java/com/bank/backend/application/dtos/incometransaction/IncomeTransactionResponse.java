package com.bank.backend.application.dtos.incometransaction;

import com.bank.backend.domain.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeTransactionResponse {
    private String id;

    private TransactionStatus transactionStatus;
}
