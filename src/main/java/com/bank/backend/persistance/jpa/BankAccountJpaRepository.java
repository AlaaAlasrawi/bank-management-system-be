package com.bank.backend.persistance.jpa;

import com.bank.backend.domain.enums.AccountStatus;
import com.bank.backend.domain.model.BankAccount;
import com.bank.backend.persistance.entity.BankAccountEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountJpaRepository extends JpaRepository<BankAccountEntity, Long> {
    @Modifying
    @Transactional
    @Query("UPDATE bank_account b SET b.status = :accountStatus WHERE b.id = :id")
    void updateAccountStatusById(@Param("id") Long id, @Param("accountStatus") AccountStatus accountStatus);


    @Query("SELECT ba.status " +
            "FROM bank_account ba " +
            "WHERE ba.accountNumber = :accountNumber")
    String accountStatus(@Param("accountNumber") String accountNumber);


    @Transactional
    @Modifying
    @Query("UPDATE bank_account b SET b.balance = b.balance + :amount, b.updatedAt = :currentTime WHERE b.id = :id")
    void updateBalance(@Param("id") Long id,
                       @Param("amount") BigDecimal amount,
                       @Param("currentTime") LocalDateTime currentTime);



    @Query("SELECT b FROM bank_account b WHERE b.user.id = :userId AND b.accountType = 'PRIMARY'")
    Optional<BankAccountEntity> findPrimaryAccountByUserId(@Param("userId") Long userId);


    List<BankAccountEntity> findAllByUser_Id(Long userId);

    Optional<BankAccountEntity> findByIban(String iban);
}
