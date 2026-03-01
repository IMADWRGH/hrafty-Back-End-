package com.hrafty.web_app.dto.request;

import com.hrafty.web_app.dto.common.AddressDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SellerRequestDTO(
        @NotBlank(message = "License number is required")
        String nbLicense,

        String imageURL,

        @NotBlank(message = "Sex is required")
        @Pattern(regexp = "M|F", message = "Sex must be M or F")
        @Size(max = 4)
        String sexe,

        @NotBlank(message = "Phone is required")
        @Pattern(regexp = "\\d{10,12}", message = "Phone must be 10-12 digits")
        @Size(max = 12)
        String phone,

        String site,

        @NotNull(message = "User ID is required")
        Long userId, //can't be null

        AddressDTO address
) {}