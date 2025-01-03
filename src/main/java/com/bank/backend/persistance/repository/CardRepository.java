package com.bank.backend.persistance.repository;

import com.bank.backend.domain.enums.CardStatus;
import com.bank.backend.domain.model.Card;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository {
    Card save(Card card);

    Card getCard(Long id);

    CardStatus setCardStatus(Long id, CardStatus cardStatus);

    Card updateCardInfo(Long id, Card card);

    List<Card> findByBankAccountId(Long id);

    void updateCardStatusByBankAccountId(Long id, CardStatus cardStatus);

    List<Card> getAllCardsByBankAccountId(Long id);
}
