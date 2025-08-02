package com.bank.backend.domain.model;


import com.bank.backend.domain.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysUser {

    private Long id;

    private String username;

    private String password;

    private String email;

    private String fullName;

    private String phoneNumber;

    private Long nationalId;

    private String nationality;

    private String dateOfBirth;

    private String address;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private UserStatus statue;
}
