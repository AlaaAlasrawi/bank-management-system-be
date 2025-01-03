package com.bank.backend.domain.services;

import com.bank.backend.domain.enums.AccountStatus;
import com.bank.backend.domain.enums.AccountType;
import com.bank.backend.domain.enums.CardStatus;
import com.bank.backend.domain.enums.CardType;
import com.bank.backend.domain.model.BankAccount;
import com.bank.backend.domain.model.BankAccountBalance;
import com.bank.backend.domain.model.SysUser;
import com.bank.backend.domain.providers.IdentityProvider;
import com.bank.backend.domain.utils.IbanUtils;
import com.bank.backend.persistance.repository.BankAccountRepository;
import com.bank.backend.persistance.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final IbanUtils ibanUtils;
    private final IdentityProvider identityProvider;
    private final CardRepository cardRepository;


    public BankAccount createBankAccount(BankAccount bankAccount) {

        SysUser user = identityProvider.currentIdentity();
        List<BankAccount> bankAccounts = bankAccountRepository.getAllByUserId(user.getId());

        AtomicReference<Boolean> primaryAccountFlag = new AtomicReference<>(false);
        AtomicReference<Boolean> savingAccountFlag = new AtomicReference<>(false);
        bankAccounts.forEach(b -> {
            if(b.getAccountType() == AccountType.PRIMARY) {
                primaryAccountFlag.set(true);
            }
            if(b.getAccountType() == AccountType.SAVINGS) {
                savingAccountFlag.set(true);
            }
        });
        if(savingAccountFlag.get() && primaryAccountFlag.get()) {
            throw new RuntimeException("you have reach the limit");
        }

        if(bankAccount.getAccountType() == AccountType.PRIMARY && primaryAccountFlag.get()) {
            throw new RuntimeException("Primary account already exists");
        }

        if(bankAccount.getAccountType() ==  AccountType.SAVINGS && savingAccountFlag.get()) {
            throw new RuntimeException("Saving account already exists");
        }

        bankAccount.setCountryCode("JO");
        bankAccount.setBankIdentifier("BKSPHERE");
        bankAccount.setBranchCode("XXX");
        bankAccount.setBalance(BigDecimal.ZERO);

        bankAccount.setUserId(user.getId());
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
        BankAccount bankAccount = bankAccountRepository.getById(id);

        if(accountStatus == AccountStatus.FROZEN && bankAccount.getAccountType() ==  AccountType.PRIMARY ) {
            cardRepository.updateCardStatusByBankAccountId(id, CardStatus.FROZEN);
        }
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

    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.getAllByUserId(identityProvider.currentIdentity().getId());
    }
}


