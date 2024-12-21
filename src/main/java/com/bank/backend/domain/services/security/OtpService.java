package com.bank.backend.domain.services.security;

import com.bank.backend.application.exception.InvalidOtpException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class OtpService {
    private static final String NO_SECRET_KEY_FOR_OTP = "no_secret_key_for_otp";
    private static final String INVALID_OR_EXPIRED_OTP = "invalid_or_expired_otp";
    private static final String VALIDATE_OTP_FIRST = "validate_otp_first";
    @Value("${bankdb.otp.length}")
    private int otpLength = 6;

    @Value("${bankdb.otp.secret-key}")
    private String otpSecretKey;

    @Value("${bankdb.otp.hash-algorithm}")
    private String otpHashAlgorithm;

    @Value("${bankdb.otp.prefix-key}")
    private String otpPrefixKey;

    @Value("${bankdb.otp.validity-time}")
    private int otpValidityTime;

    @Value("${bankdb.otp.validity.prefix-key}")
    private String otpValidityPrefixKey;

    private final RedisTemplate<String, Object> redisTemplate;

    public String generateOtp(String identity) throws NoSuchAlgorithmException, InvalidKeyException {
        SecureRandom secureRandom = new SecureRandom();
        StringBuilder otp = new StringBuilder(otpLength);

        for (int i = 0; i < otpLength; i++) {
            otp.append(secureRandom.nextInt(10));
        }

        String otpString = otp.toString();
        String hashedOtp = hashOtp(otpString);

        String otpKey = otpPrefixKey.concat(identity);
        redisTemplate.opsForValue().set(otpKey, hashedOtp, otpValidityTime, TimeUnit.MINUTES);

        return otp.toString();
    }

    public String hashOtp(String otp) throws NoSuchAlgorithmException, InvalidKeyException {
        if (otpSecretKey == null) {
            throw new IllegalStateException(NO_SECRET_KEY_FOR_OTP);
        }

        Mac mac = Mac.getInstance(otpHashAlgorithm);
        SecretKeySpec secretKeySpec = new SecretKeySpec(otpSecretKey.getBytes(StandardCharsets.UTF_8), otpHashAlgorithm);
        mac.init(secretKeySpec);

        byte[] hashBytes = mac.doFinal(otp.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }

    public Boolean verifyOtp(String otp, String identity) throws NoSuchAlgorithmException, InvalidKeyException {
        String otpKey = otpPrefixKey.concat(identity);
        String storedHashedOtp = (String) redisTemplate.opsForValue().get(otpKey);

        if (storedHashedOtp == null || storedHashedOtp.isBlank()) {
            throw new InvalidOtpException(INVALID_OR_EXPIRED_OTP);
        }

        String inputHashedOtp = hashOtp(otp);
        if (!inputHashedOtp.equals(storedHashedOtp)) {
            throw new InvalidOtpException(INVALID_OR_EXPIRED_OTP);
        }

        redisTemplate.delete(otpKey);

        String otpValidityKey = otpValidityPrefixKey.concat(identity);
        redisTemplate.opsForValue().set(otpValidityKey, true, otpValidityTime, TimeUnit.MINUTES);

        return true;
    }

    public Boolean isOtpValidated(String identity) {
        String otpValidityKey = otpValidityPrefixKey.concat(identity);
        Boolean isValidated = (Boolean) redisTemplate.opsForValue().get(otpValidityKey);

        if (isValidated == null || !isValidated) {
            throw new InvalidOtpException(VALIDATE_OTP_FIRST);
        }

        redisTemplate.delete(otpValidityKey);
        return true;
    }
}
