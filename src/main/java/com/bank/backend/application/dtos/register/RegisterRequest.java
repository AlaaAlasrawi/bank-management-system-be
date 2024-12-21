package com.bank.backend.application.dtos.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;

    private String password;

    private String email;

    private String fullName;

    private String phoneNumber;

    private Long nationalId;

    private String nationality;

    private String dateOfBirth;

    private String address;
}
