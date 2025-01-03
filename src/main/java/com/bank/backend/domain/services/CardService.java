package com.bank.backend.domain.services;

import com.bank.backend.domain.enums.CardStatus;
import com.bank.backend.domain.enums.CardType;
import com.bank.backend.domain.model.BankAccount;
import com.bank.backend.domain.model.Card;
import com.bank.backend.domain.model.SysUser;
import com.bank.backend.domain.providers.IdentityProvider;
import com.bank.backend.persistance.repository.BankAccountRepository;
import com.bank.backend.persistance.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final IdentityProvider identityProvider;
    private final BankAccountRepository bankAccountRepository;


    public Card createCard(Card card) {
        SysUser user = identityProvider.currentIdentity();
        Long bankAccountId = bankAccountRepository.getByUserId(user.getId()).getId();

        if(Objects.isNull(card.getCardType())){
            throw new IllegalArgumentException("Card type is required");
        }
       List<Card> cards = cardRepository.findByBankAccountId(bankAccountId);
        AtomicReference<Boolean> creditTypeFlag = new AtomicReference<>(false);
        AtomicReference<Boolean> debitTypeFlag = new AtomicReference<>(false);
        cards.forEach(c -> {
            if(c.getCardType() == CardType.CREDIT) {
                creditTypeFlag.set(true);
            }
            if(c.getCardType() == CardType.DEBIT) {
                debitTypeFlag.set(true);
            }
        });
        if(debitTypeFlag.get() && creditTypeFlag.get()) {
            throw new RuntimeException("you have reach the limit");
        }

        if(card.getCardType() == CardType.CREDIT && creditTypeFlag.get()) {
            throw new RuntimeException("credit card already exists");
        }

        if(card.getCardType() == CardType.DEBIT && debitTypeFlag.get()) {
            throw new RuntimeException("debit card already exists");
        }

        card.setBankAccountId(bankAccountId);
        card.setCreatedAt(LocalDateTime.now());
        card.setUpdatedAt(LocalDateTime.now());
        card.setStatus(CardStatus.ACTIVE);
        card.setCardNumber(generateCardNumber());
        card.setCvv(generateCvv());
        card.setExpiryDate(LocalDateTime.now().plusYears(4L));

        return cardRepository.save(card);
    }
    private String generateCardNumber() {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            cardNumber.append(random.nextInt(10));  // Generate a digit between 0-9
        }
        return cardNumber.toString();
    }

    private String generateCvv() {
        Random random = new Random();
        int cvv = 100 + random.nextInt(900);  // Generate a number between 100-999
        return String.valueOf(cvv);
    }

    public Card getCard(Long id) {
        return cardRepository.getCard(id);
    }

    public CardStatus setCardStatus(Long id, CardStatus cardStatus) {
        return cardRepository.setCardStatus(id,cardStatus);
    }

    public Card updateCardInfo(Long id, Card card) {
        return cardRepository.updateCardInfo(id,card);
    }

    public List<Card> getAllCards() {
        BankAccount bankAccount = bankAccountRepository.getByUserId(identityProvider.currentIdentity().getId());

        return cardRepository.getAllCardsByBankAccountId(bankAccount.getId());
    }
}
