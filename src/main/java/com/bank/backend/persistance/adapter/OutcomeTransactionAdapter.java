package com.bank.backend.persistance.adapter;

import com.bank.backend.domain.enums.TransactionStatus;
import com.bank.backend.domain.mapper.OutcomeTransactionMapper;
import com.bank.backend.domain.model.OutcomeTransaction;
import com.bank.backend.persistance.entity.OutcomeTransactionEntity;
import com.bank.backend.persistance.jpa.OutcomeTransactionJpaRepository;
import com.bank.backend.persistance.repository.OutcomeTransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OutcomeTransactionAdapter implements OutcomeTransactionRepository {
    private final OutcomeTransactionJpaRepository outcomeTransactionJpaRepository;
    private final OutcomeTransactionMapper outcomeTransactionMapper;


    @Override
    public OutcomeTransaction save(OutcomeTransaction outcomeTransaction) {
        OutcomeTransactionEntity entity = outcomeTransactionMapper.modelToEntity(outcomeTransaction);
        return outcomeTransactionMapper.entityToModel(outcomeTransactionJpaRepository.save(entity));
    }

    @Override
    public OutcomeTransaction getOutcomeTransactionById(Long id) {
        return outcomeTransactionMapper.entityToModel(outcomeTransactionJpaRepository.findById(id).orElseThrow(
                ()-> new RuntimeException("id not exist " + id)
        ));
    }

    @Override
    public Page<OutcomeTransaction> getAllOutcomeTransactions(int page, int size, String sortBy, String sortDirection) {
        Sort.Direction direction = Sort.Direction.fromString(sortDirection);
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<OutcomeTransactionEntity> entities = outcomeTransactionJpaRepository.findAll(pageable);

        List<OutcomeTransaction> tasks = entities
                .getContent()
                .stream()
                .map(outcomeTransactionMapper::entityToModel)
                .toList();

        return new PageImpl<>(tasks, pageable, entities.getTotalElements());
    }

    @Override
    public void updateStatus(Long id, TransactionStatus transactionStatus) {
        LocalDateTime currentTime = LocalDateTime.now();
        outcomeTransactionJpaRepository.updateStatus(id, currentTime, transactionStatus);
    }
}
