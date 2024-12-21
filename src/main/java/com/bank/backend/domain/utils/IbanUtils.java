package com.bank.backend.domain.utils;

import com.bank.backend.domain.model.BankAccount;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class IbanUtils {
    public String generateIban(BankAccount bankAccount) {
        // Extract values from BankAccount
        String countryCode = bankAccount.getCountryCode();  // Example: "JO"
        String accountNumber = padWithZeros(bankAccount.getAccountNumber(), 16);  // Ensure account number is 16 digits
        String bankIdentifier = padWithZeros(bankAccount.getBankIdentifier(), 4); // Ensure 4-digit bank identifier
        String branchCode = padWithZeros(bankAccount.getBranchCode(), 4);         // Ensure 4-digit branch code
        String checkDigits = "00";  // Placeholder for now, calculated later

        // Concatenate all parts: Bank Identifier + Branch Code + Account Number
        String ibanBody = bankIdentifier + branchCode + accountNumber;

        // Move the country code and placeholder check digits to the end
        String rearranged = ibanBody + countryCode + checkDigits;

        // Convert letters to numbers: A = 10, B = 11, ..., Z = 35
        String numericRepresentation = convertLettersToDigits(rearranged);

        // Calculate the check digits using modulo   numericRepresentation.append(c); 97
        BigInteger ibanNumeric = new BigInteger(numericRepresentation);
        int mod97 = ibanNumeric.mod(BigInteger.valueOf(97)).intValue();
        int calculatedCheckDigits = 98 - mod97;

        // Format the check digits to ensure it's always 2 digits (e.g., "07")
        String finalCheckDigits = String.format("%02d", calculatedCheckDigits);

        // Construct the final IBAN
        return countryCode + finalCheckDigits + ibanBody;
    }

    // Helper method to pad a string with leading zeros
    private String padWithZeros(String input, int length) {
        return String.format("%" + length + "s", input).replace(' ', '0');
    }

    // Helper method to convert letters to numeric representation
    private String convertLettersToDigits(String input) {
        StringBuilder numericRepresentation = new StringBuilder();
        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) { // A = 65, 65 - 55 = 10
                // Convert letters to numbers: A=10, B=11, ..., Z=35
                numericRepresentation.append((int) c - 55);
            } else {
                numericRepresentation.append(c);
            }
        }
        return numericRepresentation.toString();
    }
}
