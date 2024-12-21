package com.bank.backend.domain.services;

import com.bank.backend.domain.enums.AccountStatus;
import com.bank.backend.domain.enums.AccountType;
import com.bank.backend.domain.model.BankAccount;
import com.bank.backend.domain.model.BankAccountBalance;
import com.bank.backend.domain.model.SysUser;
import com.bank.backend.domain.providers.IdentityProvider;
import com.bank.backend.domain.utils.IbanUtils;
import com.bank.backend.persistance.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final IbanUtils ibanUtils;
    private final IdentityProvider identityProvider;


    public BankAccount createBankAccount(BankAccount bankAccount) {
        bankAccount.setCreatedAt(LocalDateTime.now());
        bankAccount.setUpdatedAt(LocalDateTime.now());
        bankAccount.setStatus(AccountStatus.ACTIVE);
        bankAccount.setAccountNumber(generateAccountNumber());
        bankAccount.setIban(ibanUtils.generateIban(bankAccount));

        return bankAccountRepository.createBankAccount(bankAccount);

    }

    public String generateAccountNumber() {
        // Generate a timestamp component
        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")); // 14

        // Generate a 6-digit random number -> 14 + 6 = 20
        Random random = new Random();
        int randomNumber = 100000 + random.nextInt(900000); // range: 100000 to 999999

        // Combine timestamp and random number to form the account number
        return timestamp + randomNumber;
    }
//    16 to 30 digits for account numbers.
//
//    16 is common for many banks to ensure compatibility.
//    30 is the maximum length for the account number part in certain countries like Jordan.


    public Boolean deleteBankAccount(Long id) {
        return bankAccountRepository.deleteById(id);
    }

    public BankAccount getBankAccount(Long id) {
        return bankAccountRepository.getById(id);
    }

    public Boolean updateBankAccountStatus(Long id, AccountStatus accountStatus) {
        return bankAccountRepository.updateAccountStatusById(id, accountStatus);
    }

    public BankAccountBalance getBankAccountsBalance() {
        BankAccountBalance bankAccountBalance = new BankAccountBalance();
        bankAccountBalance.setPrimaryBalance(new BigDecimal(0));
        bankAccountBalance.setSavingBalance(new BigDecimal(0));

        SysUser user = identityProvider.currentIdentity();
        Long userId = user.getId();
        List<BankAccount> bankAccounts = bankAccountRepository.getAllByUserId(userId);
        bankAccounts.forEach(bankAccount -> {
            if (bankAccount.getAccountType() == AccountType.PRIMARY) {
                bankAccountBalance.setPrimaryBalance(bankAccount.getBalance());
            }

            if (bankAccount.getAccountType() == AccountType.SAVINGS) {
                bankAccountBalance.setSavingBalance(bankAccount.getBalance());
            }
        });

        return bankAccountBalance;
    }
}
// function that generate bank
// 1- account number &
// 2- iban
// each generated number must be unique

