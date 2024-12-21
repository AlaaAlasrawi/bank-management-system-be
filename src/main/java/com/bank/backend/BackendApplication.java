package com.bank.backend;

import com.bank.backend.domain.enums.AccountStatus;
import com.bank.backend.domain.enums.AccountType;
import com.bank.backend.domain.enums.UserStatus;
import com.bank.backend.persistance.entity.BankAccountEntity;
import com.bank.backend.persistance.entity.SysUserEntity;
import com.bank.backend.persistance.jpa.BankAccountJpaRepository;
import com.bank.backend.persistance.jpa.SysUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class BackendApplication {

    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

//    @Bean
    public CommandLineRunner initUsers(SysUserJpaRepository sysUserJpaRepository) {
        return args -> {
            for (int i = 1; i <= 10; i++) {
                SysUserEntity user = new SysUserEntity();
                user.setUsername("user" + i);
                user.setPassword(passwordEncoder.encode("123"));
                user.setEmail("user" + i + "@bank.com");
                user.setFullName("Bank User " + i);
                user.setPhoneNumber("07811111" + i);
                user.setNationalId(123456L + i);
                user.setNationality("JOR");
                user.setDateOfBirth("2006-06-06");
                user.setAddress("JOR");
                user.setCreatedAt(LocalDateTime.now());
                user.setUpdatedAt(LocalDateTime.now());
                user.setStatue(UserStatus.ACTIVE);

                sysUserJpaRepository.save(user);
            }
        };
    }

//    @Bean
    public CommandLineRunner initBankAccount(BankAccountJpaRepository bankAccountJpaRepository, SysUserJpaRepository sysUserJpaRepository) {
        return args -> {
            List<SysUserEntity> users = sysUserJpaRepository.findAll();

            for (SysUserEntity user : users) {
                BankAccountEntity bank = new BankAccountEntity();

                bank.setAccountNumber("1234567890" + user.getId()); // Unique account number per user
                bank.setAccountType(AccountType.PRIMARY);
                bank.setIban("JO94CBJO00100000000001310030" + user.getId()); // Unique IBAN per user
                bank.setCountryCode("JO");
                bank.setBankIdentifier("CBJO");
                bank.setBranchCode("0010");
                bank.setCurrency("EUR");
                bank.setBalance(new BigDecimal("1500000.50")); // Example balance
                bank.setStatus(AccountStatus.ACTIVE);
                bank.setCreatedAt(LocalDateTime.now());
                bank.setUpdatedAt(LocalDateTime.now());
                bank.setUser(user);

                bankAccountJpaRepository.save(bank);
            }

            System.out.println("Bank accounts initialized for all users.");
        };
    }

}
