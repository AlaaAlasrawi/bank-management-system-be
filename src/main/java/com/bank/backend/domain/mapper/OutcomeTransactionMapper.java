package com.bank.backend.domain.mapper;

import com.bank.backend.application.dtos.outcometransaction.OutcomeTransactionRequest;
import com.bank.backend.application.dtos.outcometransaction.OutcomeTransactionResponse;
import com.bank.backend.domain.model.OutcomeTransaction;
import com.bank.backend.persistance.entity.OutcomeTransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OutcomeTransactionMapper {
    @Mapping(source="bankAccount.id",target="bankAccountId")
    OutcomeTransaction entityToModel(OutcomeTransactionEntity outcomeTransactionsEntity) ;

    @Mapping(source="bankAccountId",target="bankAccount.id")
    OutcomeTransactionEntity modelToEntity(OutcomeTransaction outcomeTransaction);

    OutcomeTransaction requestToModel(OutcomeTransactionRequest request);
    OutcomeTransactionResponse modelToResponse(OutcomeTransaction outcomeTransaction);
}
