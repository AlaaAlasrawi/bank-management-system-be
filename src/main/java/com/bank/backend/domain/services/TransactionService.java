package com.bank.backend.domain.services;


import com.bank.backend.domain.model.BankAccount;
import com.bank.backend.domain.model.SysUser;
import com.bank.backend.domain.model.Transaction;
import com.bank.backend.domain.providers.IdentityProvider;
import com.bank.backend.persistance.repository.BankAccountRepository;
import com.bank.backend.persistance.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final IdentityProvider identityProvider;

    public Page<Transaction> getAllTransactions(int page, int size, String sortBy, String sortDirection) {
        SysUser user = identityProvider.currentIdentity();
        BankAccount bankAccount = bankAccountRepository.getByUserId(user.getId());

        return transactionRepository.getAllTransactionsByBankAccountId(page,size,sortBy,sortDirection, bankAccount.getId());
    }
}
