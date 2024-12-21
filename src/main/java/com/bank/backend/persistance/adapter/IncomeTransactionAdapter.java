package com.bank.backend.persistance.adapter;

import com.bank.backend.domain.enums.TransactionStatus;
import com.bank.backend.domain.mapper.IncomeTransactionMapper;
import com.bank.backend.domain.model.IncomeTransaction;
import com.bank.backend.persistance.entity.IncomeTransactionEntity;
import com.bank.backend.persistance.jpa.IncomeTransactionJpaRepository;
import com.bank.backend.persistance.repository.IncomeTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class IncomeTransactionAdapter implements IncomeTransactionRepository {
    private final IncomeTransactionJpaRepository incomeTransactionJpaRepository;
    private final IncomeTransactionMapper incomeTransactionMapper;

    @Override
    public IncomeTransaction save(IncomeTransaction incomeTransaction) {
        IncomeTransactionEntity entity = incomeTransactionMapper.modelToEntity(incomeTransaction);
        return incomeTransactionMapper.entityToModel(incomeTransactionJpaRepository.save(entity));
    }

    @Override
    public IncomeTransaction getIncomeTransactionById(Long id) {
        return incomeTransactionMapper.entityToModel(incomeTransactionJpaRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("id not existe " + id)
        ));
    }

    @Override
    public Page<IncomeTransaction> getAllIncomeTransactions(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<IncomeTransactionEntity> entities = incomeTransactionJpaRepository.findAll(pageable);

        List<IncomeTransaction> tasks = entities
                .getContent()
                .stream()
                .map(incomeTransactionMapper::entityToModel)
                .toList();

        return new PageImpl<>(tasks, pageable, entities.getTotalElements());
    }

    @Override
    public void updateStatus(Long id, TransactionStatus transactionStatus) {
        LocalDateTime currentTime = LocalDateTime.now();
        incomeTransactionJpaRepository.updateStatus(id, currentTime, transactionStatus);
    }


}
