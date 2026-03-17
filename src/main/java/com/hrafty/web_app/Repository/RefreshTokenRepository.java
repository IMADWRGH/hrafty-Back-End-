package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.RefreshToken;
import com.hrafty.web_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUser(User user);

    boolean existsByToken(String token);


    /**
     * Reuse detection response: revokes ALL active sessions for a user.
     * Called when a previously rotated (revoked) token is presented again.
     * Returns the number of tokens revoked.
     */
    @Modifying
    @Query("UPDATE RefreshToken rt SET rt.revoked = true, rt.revokedAt = :now " +
            "WHERE rt.user = :user AND rt.revoked = false")
    int revokeAllByUser(@Param("user") User user, @Param("now") LocalDateTime now);
    default int revokeAllByUser(User user) {
        return revokeAllByUser(user, LocalDateTime.now());
    }

    /**
     * Cleanup job: delete expired or revoked tokens older than X days.
     * Schedule this with @Scheduled in a maintenance service.
     */
    @Modifying
    @Query("DELETE FROM RefreshToken rt WHERE rt.user = :user " +
            "AND (rt.expiresAt < :now OR rt.revoked = true)")
    void deleteExpiredAndRevokedByUser(@Param("user") User user, @Param("now") LocalDateTime now);
}
