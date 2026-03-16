package com.hrafty.web_app.Repository;


import com.hrafty.web_app.entities.EmailVerification;
import com.hrafty.web_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification,Long> {

    Optional<EmailVerification> findByEmailAndCodeAndUsedFalse(String email, String code);

    Optional<EmailVerification> findTopByEmailOrderByCreatedAtDesc(String email);

    void deleteByUser(User user);

    /**
     * Finds the most recent unused verification for a user.
     * Used in verifyEmail() to validate the submitted code.
     */
    Optional<EmailVerification> findTopByUserAndUsedFalseOrderByCreatedAtDesc(User user);

    /**
     * Before sending a new verification code, expire all previous unused ones
     * so the user can't use old codes.
     */

    @Modifying
    @Query("UPDATE EmailVerification ev SET ev.expiresAt = :now " +
            "WHERE ev.user.id = :userId AND ev.used = false AND ev.expiresAt > :now")
    void markAllUnusedExpiredForUser(@Param("userId") Long userId, @Param("now") LocalDateTime now);
}
