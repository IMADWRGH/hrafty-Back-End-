package com.hrafty.web_app.Repository;


import com.hrafty.web_app.entities.EmailVerification;
import com.hrafty.web_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationRepository extends JpaRepository<EmailVerification,Long> {

    Optional<EmailVerification> findByEmailAndCodeAndUsedFalse(String email, String code);

    Optional<EmailVerification> findTopByEmailOrderByCreatedAtDesc(String email);

    void deleteByUser(User user);
}
