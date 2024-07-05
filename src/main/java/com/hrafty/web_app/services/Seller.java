package com.hrafty.web_app.services;


import com.hrafty.web_app.dto.SellerDTO;



public interface Seller {
    SellerDTO getSeller(Long id);

    SellerDTO updateSeller(SellerDTO sellerDTO,Long id);
}
