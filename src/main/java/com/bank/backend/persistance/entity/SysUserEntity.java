package com.bank.backend.persistance.entity;

import com.bank.backend.domain.enums.UserStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity(name="sys_user")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="sys_user")
public class SysUserEntity {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="email")
    private String email;

    @Column(name="fullName")
    private String fullName;

    @Column(name="phoneNumber")
    private String phoneNumber;

    @Column(name="nationlId")
    private Long nationalId;

    @Column(name="nationality")
    private String nationality;

    @Column(name="date_of_birth")
    private String dateOfBirth;

    @Column(name="address")
    private String address;

    @Column(name="created_at")
    private LocalDateTime createdAt;

    @Column(name="updated_at")
    private LocalDateTime updatedAt;

    @Column(name="statue")
    private UserStatus statue;

    @OneToMany(mappedBy = "user")
    private Set<BankAccountEntity> bankAccounts;
}
