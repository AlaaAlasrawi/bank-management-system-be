package com.bank.backend.application.controllers;

import com.bank.backend.application.dtos.bankaccount.CreateBankAccountRequest;
import com.bank.backend.application.dtos.bankaccount.CreateBankAccountResponse;
import com.bank.backend.domain.enums.AccountStatus;
import com.bank.backend.domain.mapper.BankAccountMapper;
import com.bank.backend.domain.model.BankAccount;
import com.bank.backend.domain.model.BankAccountBalance;
import com.bank.backend.domain.services.BankAccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bankAccount")
@RequiredArgsConstructor
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @PostMapping
    public ResponseEntity<CreateBankAccountResponse> createBankAccount(@RequestBody CreateBankAccountRequest request) {
        BankAccount bankAccount = bankAccountMapper.requestToModel(request);
        return ResponseEntity.ok(bankAccountMapper.modelToResponse(bankAccountService.createBankAccount(bankAccount)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteBankAccount(@PathVariable Long id){
        return ResponseEntity.ok(bankAccountService.deleteBankAccount(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BankAccount> getBankAccount(@PathVariable Long id){
        return ResponseEntity.ok(bankAccountService.getBankAccount(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateBankAccountStatus(@PathVariable Long id,@RequestParam("status") String status){
        AccountStatus accountStatus = AccountStatus.valueOf(status.toUpperCase());
        return  ResponseEntity.ok(bankAccountService.updateBankAccountStatus(id, accountStatus));
    }

    @GetMapping
    public ResponseEntity<BankAccountBalance> getBankAccountsBalance(){
        return ResponseEntity.ok(bankAccountService.getBankAccountsBalance());
    }

    @GetMapping("/all")
    public ResponseEntity<List<BankAccount>> getAllBankAccounts(){
        return  ResponseEntity.ok(bankAccountService.getAllBankAccounts());
    }
}


