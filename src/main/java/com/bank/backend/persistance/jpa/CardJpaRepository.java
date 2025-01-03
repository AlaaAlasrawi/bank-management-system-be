package com.bank.backend.persistance.jpa;

import com.bank.backend.domain.enums.CardStatus;
import com.bank.backend.domain.enums.CardType;
import com.bank.backend.domain.model.Card;
import com.bank.backend.persistance.entity.CardEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CardJpaRepository extends JpaRepository<CardEntity,Long> {
    @Modifying
    @Transactional
    @Query("UPDATE card c SET c.status = :status, c.updatedAt = :updatedAt WHERE c.id = :id")
    void updateCardStatus(@Param("id") Long id,
                         @Param("status") CardStatus status,
                         @Param("updatedAt") LocalDateTime updatedAt);
    @Query("UPDATE card c SET c.status = :status,c.cardType=:cardType ,c.updatedAt = :updatedAt WHERE c.id = :id")
    void updateCardInfo(@Param("id") Long id,
                        @Param("status") CardStatus cardStatus,
                        @Param("status") CardType cardType,
                        @Param("updatedAt") LocalDateTime updatedAt);


    @Modifying
    @Transactional
    @Query("UPDATE card c SET c.status = :cardStatus WHERE c.bankAccount.id = :bankAccountId")
    void updateCardStatusByBankAccountId(@Param("bankAccountId") Long bankAccountId,
                                         @Param("cardStatus") CardStatus cardStatus);

    List<CardEntity> findAllByBankAccount_Id(Long id);
}
