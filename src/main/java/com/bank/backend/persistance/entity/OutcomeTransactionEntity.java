package com.bank.backend.persistance.entity;

import com.bank.backend.domain.enums.OutcomeMethods;
import com.bank.backend.domain.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "outcome_transaction")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "outcome_transaction")
public class OutcomeTransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "outcome_methods")
    private OutcomeMethods outcomeMethods;

    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;

    @Column(name = "currency")
    private String currency;

    @Column(name = "reference")
    private String reference;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TransactionStatus status;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "bank_account_id" ,referencedColumnName = "id")
    private BankAccountEntity bankAccount;

    @Column(name = "source_bank_account_id")
    private Long sourceBankAccountId;
}
