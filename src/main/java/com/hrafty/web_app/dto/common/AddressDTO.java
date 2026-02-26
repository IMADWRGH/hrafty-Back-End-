package com.hrafty.web_app.dto.common;

public record AddressDTO(
        Long id,
        String street,
        String shopNumber,
        String cityName,
        String regionalName
        ) {
}
