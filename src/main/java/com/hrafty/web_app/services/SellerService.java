package com.hrafty.web_app.services;


import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface SellerService {
    SellerDTO getSeller(Long id);

    SellerDTO updateSeller(SellerDTO sellerDTO,Long id);

    SellerDTO create(UserDTO userDTO, SellerDTO sellerDTO, MultipartFile file);

    Page<SellerDTO> getAllSellers(Pageable pageable);
}
