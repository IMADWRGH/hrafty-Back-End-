package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.request.*;
import com.hrafty.web_app.dto.response.*;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {

    UserResponseDTO register(UserRequestDTO request);

    EmailVerifiedResponseDTO verifyEmail(VerifyEmailRequestDTO request);

    AuthResponseDTO completeCustomerProfile(CustomerRequestDTO request, Long authenticatedUserId);

    AuthResponseDTO completeSellerProfile(SellerRequestDTO request, Long authenticatedUserId);

    AuthResponseDTO login(LoginRequestDTO request);

    TokenRefreshResponseDTO refreshToken(RefreshTokenRequestDTO request);

    void logout(RefreshTokenRequestDTO request);

    ResendVerificationResponseDTO resendVerificationCode(ResendVerificationRequestDTO request);
}