package com.bank.backend.persistance.repository;

import com.bank.backend.domain.enums.CardStatus;
import com.bank.backend.domain.model.BankAccount;
import com.bank.backend.domain.model.Card;
import com.bank.backend.persistance.entity.CardEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository {
    Card save(Card card);

    Card getCard(Long id);

    CardStatus setCardStatus(Long id, CardStatus cardStatus);

    Card updateCardInfo(Long id, Card card);

    List<Card> getAllCards();

    List<Card> findByBankAccountId(Long id);
}
