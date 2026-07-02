package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.request.*;
import com.hrafty.web_app.dto.response.*;

public interface AuthService {

    UserResponseDTO register(UserRequestDTO request);

    EmailVerifiedResponseDTO verifyEmail(VerifyEmailRequestDTO request);

    AuthResponseDTO completeCustomerProfile(CustomerRequestDTO request, Long authenticatedUserId);

    AuthResponseDTO completeSellerProfile(SellerRequestDTO request, Long authenticatedUserId);

    AuthResponseDTO login(LoginRequestDTO request, String ipAddress);

    TokenRefreshResponseDTO refreshToken(RefreshTokenRequestDTO request);

    TokenRefreshResponseDTO refreshToken(String token, String ipAddress);

    void logout(RefreshTokenRequestDTO request);

    void logout(String refreshToken);

    ResendVerificationResponseDTO resendVerificationCode(ResendVerificationRequestDTO request);
}
