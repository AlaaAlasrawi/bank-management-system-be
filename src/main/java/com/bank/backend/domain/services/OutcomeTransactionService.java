package com.bank.backend.domain.services;

import com.bank.backend.domain.enums.IncomeMethods;
import com.bank.backend.domain.enums.TransactionStatus;
import com.bank.backend.domain.model.BankAccount;
import com.bank.backend.domain.model.IncomeTransaction;
import com.bank.backend.domain.model.OutcomeTransaction;
import com.bank.backend.domain.model.SysUser;
import com.bank.backend.domain.providers.IdentityProvider;
import com.bank.backend.domain.utils.AccountUtils;
import com.bank.backend.persistance.repository.BankAccountRepository;
import com.bank.backend.persistance.repository.IncomeTransactionRepository;
import com.bank.backend.persistance.repository.OutcomeTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OutcomeTransactionService {

    private final OutcomeTransactionRepository outcomeTransactionRepository;
    private final IncomeTransactionRepository incomeTransactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final AccountUtils accountUtils;
    private final IdentityProvider identityProvider;

    public OutcomeTransaction createOutcomeTransaction(OutcomeTransaction outcomeTransaction, String iban) {
        SysUser user = identityProvider.currentIdentity();

        if(outcomeTransaction.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        BankAccount destinationBankAccount = bankAccountRepository.getByIban(iban);
        outcomeTransaction.setBankAccountId(destinationBankAccount.getId());
        BankAccount sourceBankAccount = bankAccountRepository.getByUserId(user.getId());

        fillUpMissingInfo(outcomeTransaction, sourceBankAccount);
        validate(outcomeTransaction, sourceBankAccount, destinationBankAccount);
        updateIncomeForDestination(outcomeTransaction, destinationBankAccount);

        return outcomeTransactionRepository.save(outcomeTransaction);
    }

    public OutcomeTransaction getOutcomeTransactionById(Long id) {
        return outcomeTransactionRepository.getOutcomeTransactionById(id);
    }

    public Page<OutcomeTransaction> getAllOutcomeTransactions(int page, int size, String sortBy, String sortDirection) {
        return outcomeTransactionRepository.getAllOutcomeTransactions(page, size, sortBy, sortDirection);
    }

    public OutcomeTransaction retryOutcomeTransaction(Long id) {
        OutcomeTransaction outcomeTransaction = getOutcomeTransactionById(id);
        if (!outcomeTransaction.getStatus().equals(TransactionStatus.FAILED)) {
            throw new RuntimeException("transaction status is not FAILED");
        }

        outcomeTransaction.setStatus(TransactionStatus.PENDING);

        BankAccount bankAccount = bankAccountRepository.getById(outcomeTransaction.getBankAccountId());
        if (accountUtils.isAccountActive(bankAccount.getAccountNumber())) {
            outcomeTransactionRepository.updateStatus(id, TransactionStatus.COMPLETED);
            bankAccountRepository.updateBalance(bankAccount.getId(), outcomeTransaction.getAmount().multiply(new BigDecimal(-1)));
        }
        return getOutcomeTransactionById(id);
    }

    private void updateIncomeForDestination(OutcomeTransaction outcomeTransaction, BankAccount destinationBankAccount) {
        IncomeTransaction incomeTransaction = IncomeTransaction.builder()
                .amount(outcomeTransaction.getAmount())
                .incomeMethods(IncomeMethods.BANK_TRANSFER)
                .description(outcomeTransaction.getDescription())
                .currency(outcomeTransaction.getCurrency())
                .status(TransactionStatus.COMPLETED)
                .transactionDate(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .bankAccountId(destinationBankAccount.getId())
                .build();
        incomeTransactionRepository.save(incomeTransaction);
    }

    private void validate(OutcomeTransaction outcomeTransaction, BankAccount sourceBankAccount, BankAccount destinationBankAccount) {
        validateAmount(outcomeTransaction, sourceBankAccount);
        validateAccountStatus(outcomeTransaction, sourceBankAccount, destinationBankAccount);
    }

    private void validateAccountStatus(OutcomeTransaction outcomeTransaction, BankAccount sourceBankAccount, BankAccount destinationBankAccount) {

        if (!accountUtils.isAccountActive(destinationBankAccount.getAccountNumber()) || !accountUtils.isAccountActive(sourceBankAccount.getAccountNumber())) {
            outcomeTransaction.setStatus(TransactionStatus.FAILED);
            outcomeTransactionRepository.save(outcomeTransaction);
            throw new RuntimeException("Transaction failed");
        } else {
            outcomeTransaction.setStatus(TransactionStatus.COMPLETED);
            bankAccountRepository.updateBalance(destinationBankAccount.getId(), outcomeTransaction.getAmount());
            bankAccountRepository.updateBalance(sourceBankAccount.getId(), outcomeTransaction.getAmount().multiply(new BigDecimal(-1)));
        }
    }

    private void validateAmount(OutcomeTransaction outcomeTransaction, BankAccount sourceBankAccount) {
        if (sourceBankAccount.getBalance().compareTo(outcomeTransaction.getAmount()) < 0) {
            outcomeTransaction.setStatus(TransactionStatus.FAILED);
            outcomeTransactionRepository.save(outcomeTransaction);
            throw new RuntimeException("Not enough balance");
        }
    }

    private void fillUpMissingInfo(OutcomeTransaction outcomeTransaction, BankAccount sourceBankAccount) {
        outcomeTransaction.setUpdatedAt(LocalDateTime.now());
        outcomeTransaction.setTransactionDate(LocalDateTime.now());
        outcomeTransaction.setStatus(TransactionStatus.PENDING);
        outcomeTransaction.setSourceBankAccountId(sourceBankAccount.getId());
    }
}
