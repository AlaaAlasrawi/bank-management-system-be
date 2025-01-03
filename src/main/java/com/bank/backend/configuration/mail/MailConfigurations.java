package com.bank.backend.configuration.mail;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Slf4j
@Configuration
public class MailConfigurations {

    private static final String MAIL_TRANSPORT_PROTOCOL_KEY = "mail.transport.protocol";
    private static final String MAIL_SMTP_AUTH_KEY = "mail.smtp.auth";
    private static final String MAIL_SMTP_STARTTLS_KEY = "mail.smtp.starttls.enable";
    private static final String MAIL_SMTP_START_DEBUG_KEY = "mail.debug";
    private static final String MAIL_SMTP_SSL_ENABLED_KEY = "mail.smtp.ssl.enable";

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private int port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.properties.mail.transport.protocol}")
    private String protocol;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String isStarttlsEnabled;

    @Value("${spring.mail.properties.mail.smtp.ssl.enable}")
    private String isSslEnabled;

    @Value("${spring.mail.properties.mail.debug}")
    private String isDebugEnabled;

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);


        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put(MAIL_TRANSPORT_PROTOCOL_KEY, protocol);
        props.put(MAIL_SMTP_AUTH_KEY, smtpAuth);
        props.put(MAIL_SMTP_STARTTLS_KEY, isStarttlsEnabled);
        props.put(MAIL_SMTP_START_DEBUG_KEY, isDebugEnabled);
        props.put(MAIL_SMTP_SSL_ENABLED_KEY, isSslEnabled);

        return mailSender;
    }
}