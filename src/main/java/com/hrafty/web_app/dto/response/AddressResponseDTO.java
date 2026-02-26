package com.hrafty.web_app.dto.response;

public record AddressResponseDTO(
        Long id,
        String street,
        String shopNumber,
        String cityName,
        String regionalName,
        String fullAddress  // street + shopNumber + city
) {}