package com.bank.backend.application.dtos.card;

import com.bank.backend.domain.enums.CardStatus;
import com.bank.backend.domain.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCardRequest {
    private CardType cardType;
    private CardStatus cardStatus;
    private Long bankAccountId;
}
