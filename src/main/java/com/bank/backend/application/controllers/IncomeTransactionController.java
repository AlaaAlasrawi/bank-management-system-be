package com.bank.backend.application.controllers;

import com.bank.backend.application.dtos.incometransaction.IncomeTransactionRequest;
import com.bank.backend.application.dtos.incometransaction.IncomeTransactionResponse;
import com.bank.backend.domain.mapper.IncomeTransactionMapper;
import com.bank.backend.domain.model.IncomeTransaction;
import com.bank.backend.domain.services.IncomeTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/income-transaction")
public class IncomeTransactionController {
    private final IncomeTransactionService incomeTransactionService;
    private final IncomeTransactionMapper incomeTransactionMapper;


    @PostMapping
    public ResponseEntity<IncomeTransactionResponse> createIncomeTransaction(@RequestBody IncomeTransactionRequest request) {
        IncomeTransaction incomeTransaction = incomeTransactionMapper.requestToModel(request);
        return ResponseEntity.ok(incomeTransactionMapper.modelToResponse(incomeTransactionService.createIncomeTransaction(incomeTransaction)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncomeTransactionResponse> retryIncomeTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(incomeTransactionMapper.modelToResponse(incomeTransactionService.retryIncomeTransaction(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncomeTransaction> getIncomeTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(incomeTransactionService.getIncomeTransactionById(id));
    }

    @GetMapping
    public ResponseEntity<Page<IncomeTransaction>> getAllIncomeTransactions(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size,
            @RequestParam(defaultValue = "transactionDate", name = "sortBy") String sortBy,
            @RequestParam(defaultValue = "desc", name = "sortDirection") String sortDirection) {
        return ResponseEntity.ok(incomeTransactionService.getAllIncomeTransactions(page, size, sortBy, sortDirection));
    }
}




