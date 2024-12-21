package com.bank.backend.domain.mapper;

import com.bank.backend.application.dtos.bankaccount.CreateBankAccountRequest;
import com.bank.backend.application.dtos.bankaccount.CreateBankAccountResponse;
import com.bank.backend.domain.model.BankAccount;
import com.bank.backend.persistance.entity.BankAccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccount requestToModel(CreateBankAccountRequest request);
    CreateBankAccountResponse modelToResponse(BankAccount model);

    BankAccount entityToModel(BankAccountEntity byId);

    BankAccountEntity ModelToEntity(BankAccount bankAccount);
}
