package com.bank.backend.application.dtos.outcometransaction;

import com.bank.backend.domain.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OutcomeTransactionResponse {
    private Long id;

    private TransactionStatus transactionStatus;
}
