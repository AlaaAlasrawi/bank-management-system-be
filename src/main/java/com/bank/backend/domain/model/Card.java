package com.bank.backend.domain.model;

import com.bank.backend.domain.enums.CardStatus;
import com.bank.backend.domain.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private Long id;

    private String cardNumber;

    private CardType cardType;

    private LocalDateTime expiryDate;

    private String cvv;

    private CardStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long bankAccountId;
}
