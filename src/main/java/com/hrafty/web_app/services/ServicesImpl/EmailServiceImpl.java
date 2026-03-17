package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Sends HTML emails using Thymeleaf templates.
 *
 * Template files are located at:
 *   src/main/resources/templates/email/verification-code.html
 *   src/main/resources/templates/email/welcome.html
 *
 * Both methods are @Async — email sending happens in a background thread
 * so the HTTP response is returned to the user immediately without waiting
 * for the SMTP server. Add @EnableAsync to your main application class.
 *
 * Variables passed to each template:
 *
 *   verification-code.html:
 *     ${verificationCode}   — the 6-digit code string
 *     ${expirationMinutes}  — integer (15)
 *     ${frontendUrl}        — base URL for footer links
 *
 *   welcome.html:
 *     ${fullName}           — user's display name
 *     ${frontendUrl}        — base URL for CTA button and footer links
 */

@Service
public class EmailServiceImpl implements EmailService {


    private static final Logger log = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(JavaMailSender mailSender, TemplateEngine templateEngine, String fromEmail, String frontendUrl) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
        this.fromEmail = fromEmail;
        this.frontendUrl = frontendUrl;
    }

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Value("${app.frontend.url:http://localhost:4200}")
    private String frontendUrl;

    // ─────────────────────────────────────────────────────────────────────────
    // Verification code email — sent after registration (Step 1)
    // Template: src/main/resources/templates/email/verification-code.html
    // ─────────────────────────────────────────────────────────────────────────
    @Async
    @Override
    public void sendVerificationCode(String toEmail, String code) {
        try {
            // Build Thymeleaf context — variables become ${...} in the template
            Context context = new Context();
            context.setVariable("verificationCode", code);
            context.setVariable("expirationMinutes", 15);
            context.setVariable("frontendUrl", frontendUrl);

            // Process template → produces the final HTML string
            String htmlContent = templateEngine.process("email/verification-code", context);

            send(toEmail, "Verify Your Email — Hrafty", htmlContent);

            log.info("Verification email sent to: {}", toEmail);

        } catch (MessagingException e) {
            log.error("Failed to send verification email to {}: {}", toEmail, e.getMessage());
            // Re-throw as unchecked so the transaction rolls back if needed
            throw new RuntimeException("Failed to send verification email", e);
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Welcome email — sent after email is verified (Step 2)
    // Template: src/main/resources/templates/email/welcome.html
    // ─────────────────────────────────────────────────────────────────────────
    @Async
    @Override
    public void sendWelcomeEmail(String toEmail, String fullName) {
        try {
            Context context = new Context();
            context.setVariable("fullName", fullName);
            context.setVariable("frontendUrl", frontendUrl);

            String htmlContent = templateEngine.process("email/welcome", context);

            send(toEmail, "Welcome to Hrafty! 🎉", htmlContent);

            log.info("Welcome email sent to: {}", toEmail);

        } catch (MessagingException e) {
            // Welcome email failure should NOT fail the verification flow
            // Log it and continue — user is already verified
            log.error("Failed to send welcome email to {}: {}", toEmail, e.getMessage());
        }
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Password reset (implement when needed)
    // ─────────────────────────────────────────────────────────────────────────
    @Async
    @Override
    public void sendPasswordResetEmail(String toEmail, String token) {
        // TODO: create src/main/resources/templates/email/password-reset.html
        // Context variables needed: resetLink = frontendUrl + "/reset-password?token=" + token
        log.info("Password reset email not yet implemented for: {}", toEmail);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Private helper — creates and sends the MimeMessage
    // ─────────────────────────────────────────────────────────────────────────
    private void send(String toEmail, String subject, String htmlContent)
            throws MessagingException {

        MimeMessage message = mailSender.createMimeMessage();
        // multipart=true allows HTML; charset=UTF-8 for emoji and special chars
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom(fromEmail);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(htmlContent, true); // true = isHtml

        mailSender.send(message);
    }
}