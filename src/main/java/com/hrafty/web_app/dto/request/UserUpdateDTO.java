package com.hrafty.web_app.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateDTO(
        @Size(min = 2, max = 100)
        String fullName,

        @Email(message = "Invalid email format")
        String email,

        @Size(min = 8, message = "Password must be at least 8 characters")
        String password  // if pwd == null  so don't change pwd
) {
}
