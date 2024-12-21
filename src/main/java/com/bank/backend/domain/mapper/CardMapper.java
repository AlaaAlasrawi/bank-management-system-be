package com.bank.backend.domain.mapper;

import com.bank.backend.application.dtos.card.CreateCardRequest;
import com.bank.backend.application.dtos.card.CreateCardResponse;
import com.bank.backend.application.dtos.card.UpdateCardRequest;
import com.bank.backend.domain.model.Card;
import com.bank.backend.persistance.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {
    Card requestToModel(CreateCardRequest request);
    Card requestToModel(UpdateCardRequest request);
    CreateCardResponse modelToResponse(Card card);

    @Mapping(source = "bankAccountId", target = "bankAccount.id")
    CardEntity modelToEntity(Card card);

    @Mapping(source = "bankAccount.id", target = "bankAccountId")
    Card entityToModel(CardEntity entity);
}
