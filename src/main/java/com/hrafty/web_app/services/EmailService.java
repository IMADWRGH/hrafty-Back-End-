package com.hrafty.web_app.services;

public interface EmailService {

    void sendVerificationCode(String toEmail, String code);

    void sendWelcomeEmail(String toEmail, String fullName);

    void sendPasswordResetEmail(String toEmail, String token);
}
