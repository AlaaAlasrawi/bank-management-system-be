package com.bank.backend.domain.utils;

import com.bank.backend.persistance.repository.BankAccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountUtils {
    private final BankAccountRepository bankAccountRepository;
    public Boolean isAccountActive(String accountNumber) {
        return bankAccountRepository.isAccountActive(accountNumber);
    }
}
