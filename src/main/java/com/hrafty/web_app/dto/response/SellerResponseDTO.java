package com.hrafty.web_app.dto.response;

import com.hrafty.web_app.dto.common.AddressDTO;
import java.time.LocalDateTime;

public record SellerResponseDTO(
        Long id,
        String nbLicense,
        String imageURL,
        String sexe,
        String phone,
        String site,
        Long userId,
        String userEmail,
        AddressDTO address,
        Integer productCount,
        Integer serviceCount,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}