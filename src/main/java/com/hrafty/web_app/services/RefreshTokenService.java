package com.hrafty.web_app.services;

import com.hrafty.web_app.entities.RefreshToken;
import com.hrafty.web_app.entities.User;

import java.util.Optional;

public interface RefreshTokenService {

    Optional<RefreshToken> findByToken(String token);

    public RefreshToken createRefreshToken(User user);

    public boolean isTokenValid(RefreshToken token);

    public void deleteByUser(User user);
}
