package com.bank.backend.domain.mapper;


import com.bank.backend.application.dtos.incometransaction.IncomeTransactionRequest;
import com.bank.backend.application.dtos.incometransaction.IncomeTransactionResponse;
import com.bank.backend.domain.model.IncomeTransaction;
import com.bank.backend.persistance.entity.IncomeTransactionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IncomeTransactionMapper {
    IncomeTransaction requestToModel(IncomeTransactionRequest incomeTransactionRequest);

    @Mapping(source="bankAccountId",target="bankAccount.id")
    IncomeTransactionEntity modelToEntity(IncomeTransaction incomeTransaction);

    @Mapping(source="bankAccount.id",target="bankAccountId")
    IncomeTransaction entityToModel(IncomeTransactionEntity incomeTransactionEntity);

    IncomeTransactionResponse modelToResponse(IncomeTransaction incomeTransaction);
}
