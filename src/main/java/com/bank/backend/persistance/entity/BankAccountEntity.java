package com.bank.backend.persistance.entity;

import com.bank.backend.domain.enums.AccountStatus;
import com.bank.backend.domain.enums.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Entity(name = "bank_account")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "bank_account")
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="account_number")
    private String accountNumber;

    @Column(name="account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column(name = "iban") //International Bank Account Number
    private String iban;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "bank_identifier")
    private String bankIdentifier;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "currency")
    private String currency;

    @Column(name="balance")
    private BigDecimal balance;

    @Column(name= "status")
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "user_id" ,referencedColumnName = "id")
    private SysUserEntity user;

    @OneToMany(mappedBy = "bankAccount")
    private Set<CardEntity> cards;

    @OneToMany(mappedBy = "bankAccount")
    private Set<IncomeTransactionEntity> incomeTransactions;

    @OneToMany(mappedBy = "bankAccount")
    private Set<OutcomeTransactionEntity> outcomeTransactions;
}
