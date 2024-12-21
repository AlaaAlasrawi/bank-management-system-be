package com.bank.backend.application.exception;

public class PhoneNumberNotValidException extends RuntimeException {
    public PhoneNumberNotValidException(String message) {
        super(message);
    }
}
