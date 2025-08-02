package com.bank.backend.domain.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OtpHtmlUtils {

    public static String generateHtmlResponse(String otp) {
        return """
                <!DOCTYPE html>
                <html>
                <body style="font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f4f4f9; color: #333333;">
                    <div style="max-width: 600px; margin: 0 auto; background-color: #ffffff; border: 1px solid #dddddd; border-radius: 8px; overflow: hidden; box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);">
                        <div style="background-color: #0048ba; color: #ffffff; padding: 20px; text-align: center;">
                            <h1 style="margin: 0; font-size: 24px;">Welcome to Banksphere</h1>
                            <p style="margin: 0;">Your trusted partner in financial innovation</p>
                        </div>
                        <div style="padding: 30px; line-height: 1.6;">
                            <p>Dear Valued Customer,</p>
                            <p>We are excited to have you onboard with Banksphere. As part of our secure authentication process, we have generated a one-time password (OTP) for you to complete your recent action.</p>
                            <div style="font-size: 28px; font-weight: bold; color: #0048ba; margin: 20px 0; text-align: center;">""" + otp + """
                            </div>
                            <p>Please use the above code within the next 10 minutes to ensure your action is completed securely. Remember, this code is unique to you and should not be shared with anyone for your safety.</p>
                            <a href="https://www.banksphere.com" style="display: inline-block; background-color: #0048ba; color: #ffffff; text-decoration: none; padding: 12px 20px; border-radius: 4px; margin-top: 20px; font-size: 16px; font-weight: bold; text-align: center;">Visit Banksphere</a>
                            <p style="font-size: 14px; color: #555555; margin-top: 20px;">
                                If you did not request this OTP or suspect any suspicious activity, please contact our support team immediately at 
                                <a href="mailto:support@banksphere.com" style="color: #0048ba;">support@banksphere.com</a>.
                            </p>
                        </div>
                        <div style="background-color: #f4f4f9; padding: 15px; text-align: center; font-size: 12px; color: #555555;">
                            <p>&copy; 2025 Banksphere. All rights reserved.</p>
                            <p>Your security is our top priority.</p>
                        </div>
                    </div>
                </body>
                </html>
                """;


    }
}
