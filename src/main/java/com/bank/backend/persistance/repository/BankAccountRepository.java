package com.bank.backend.persistance.repository;

import com.bank.backend.domain.enums.AccountStatus;
import com.bank.backend.domain.model.BankAccount;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BankAccountRepository {
    Boolean deleteById(Long id);

    Boolean updateAccountStatusById(Long id, AccountStatus accountStatus);

    BankAccount getById(Long id);

    BankAccount createBankAccount(BankAccount bankAccount);

    Boolean isAccountActive(String accountNumber);

    void updateBalance(Long id, BigDecimal amount);

    BankAccount getByUserId(Long userId);

    List<BankAccount> getAllByUserId(Long userId);

    BankAccount getByIban(String iban);
}
