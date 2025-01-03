package com.bank.backend.application.controllers;

import com.bank.backend.application.dtos.outcometransaction.OutcomeTransactionRequest;
import com.bank.backend.application.dtos.outcometransaction.OutcomeTransactionResponse;
import com.bank.backend.domain.mapper.OutcomeTransactionMapper;
import com.bank.backend.domain.model.OutcomeTransaction;
import com.bank.backend.domain.services.OutcomeTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/outcome-transaction")
public class OutcomeTransactionController {
    private final OutcomeTransactionService outcomeTransactionService;
    private final OutcomeTransactionMapper outcomeTransactionMapper;

    @PostMapping
    public ResponseEntity<OutcomeTransactionResponse> createOutcomeTransaction(@RequestBody OutcomeTransactionRequest request) {
        OutcomeTransaction outcomeTransaction = outcomeTransactionMapper.requestToModel(request);
        return ResponseEntity.ok(outcomeTransactionMapper.modelToResponse(outcomeTransactionService.createOutcomeTransaction(outcomeTransaction, request.getIban())));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OutcomeTransactionResponse> retryOutcomeTransaction(@PathVariable Long id) {
        return ResponseEntity.ok(outcomeTransactionMapper.modelToResponse(outcomeTransactionService.retryOutcomeTransaction(id)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OutcomeTransaction> getIncomeTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(outcomeTransactionService.getOutcomeTransactionById(id));
    }

    @GetMapping
    public ResponseEntity<Page<OutcomeTransaction>> getAllOutcomeTransactions(
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "10", name = "size") int size,
            @RequestParam(defaultValue = "transactionDate", name = "sortBy") String sortBy,
            @RequestParam(defaultValue = "desc", name = "sortDirection") String sortDirection) {
        return ResponseEntity.ok(outcomeTransactionService.getAllOutcomeTransactions(page, size, sortBy, sortDirection));
    }
}

