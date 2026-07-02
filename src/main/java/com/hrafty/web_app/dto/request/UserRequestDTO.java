package com.hrafty.web_app.dto.request;

import com.hrafty.web_app.entities.enums.Role;
import jakarta.validation.constraints.*;

public record UserRequestDTO(
        @NotBlank(message = "Full name is required")
        @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 128, message = "Password must be between 8 and 128 characters")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&#^()_\\-+=.])[A-Za-z\\d@$!%*?&#^()_\\-+=.]{8,128}$",
                message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character"
        )
        String password,

         @NotNull(message = "Role is required")
         Role role
) {}
