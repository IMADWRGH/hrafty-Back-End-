package com.hrafty.web_app.dto.response;

public record SellerSummaryDTO(
        Long id,
        String nbLicense,
        String phone,
        String cityName // extract from address :)
) {}