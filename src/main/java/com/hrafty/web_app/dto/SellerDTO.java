package com.hrafty.web_app.dto;

public record SellerDTO(Long id,Long nb_license,String imageURL,String sexe,String phone,String site,Long userId,AddressDTO addressId) {
}

