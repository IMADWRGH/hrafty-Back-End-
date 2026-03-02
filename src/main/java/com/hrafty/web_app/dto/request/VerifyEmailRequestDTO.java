package com.hrafty.web_app.dto.request;

import jakarta.validation.constraints.*;

public record VerifyEmailRequestDTO(
        @NotBlank @Email String email,
        @NotBlank @Size(min = 6, max = 6) String code
) {}