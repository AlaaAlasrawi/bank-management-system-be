package com.bank.backend.domain.services;

import com.bank.backend.domain.enums.TransactionStatus;
import com.bank.backend.domain.model.BankAccount;
import com.bank.backend.domain.model.IncomeTransaction;
import com.bank.backend.domain.model.OutcomeTransaction;
import com.bank.backend.domain.model.SysUser;
import com.bank.backend.domain.providers.IdentityProvider;
import com.bank.backend.domain.utils.AccountUtils;
import com.bank.backend.persistance.repository.BankAccountRepository;
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
    private final BankAccountRepository bankAccountRepository;
    private final AccountUtils accountUtils;
    private final IdentityProvider identityProvider;


    public OutcomeTransaction createOutcomeTransaction(OutcomeTransaction outcomeTransaction) {
        SysUser user = identityProvider.currentIdentity();

        BankAccount destinationBankAccount = bankAccountRepository.getById(outcomeTransaction.getBankAccountId());
        BankAccount sourceBankAccount = bankAccountRepository.getByUserId(user.getId());

        outcomeTransaction.setUpdatedAt(LocalDateTime.now());
        outcomeTransaction.setTransactionDate(LocalDateTime.now());
        outcomeTransaction.setStatus(TransactionStatus.PENDING);
        outcomeTransaction.setSourceBankAccountId(sourceBankAccount.getId());


        if (!accountUtils.isAccountActive(destinationBankAccount.getAccountNumber()) && !accountUtils.isAccountActive(sourceBankAccount.getAccountNumber())) {
            outcomeTransaction.setStatus(TransactionStatus.FAILED);
        } else {
            outcomeTransaction.setStatus(TransactionStatus.COMPLETED);
            bankAccountRepository.updateBalance(destinationBankAccount.getId(), outcomeTransaction.getAmount());
            bankAccountRepository.updateBalance(sourceBankAccount.getId(), outcomeTransaction.getAmount().multiply(new BigDecimal(-1)));
        }

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

}
