package com.hrafty.web_app.Repository;

import com.hrafty.web_app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByFullName(String fullName);
    boolean existsByEmail(String email);
}
