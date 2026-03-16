package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.request.SellerRequestDTO;
import com.hrafty.web_app.dto.request.UserRequestDTO;
import com.hrafty.web_app.dto.response.SellerResponseDTO;
import com.hrafty.web_app.dto.response.SellerSummaryDTO;
import com.hrafty.web_app.dto.common.PageResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SellerService {

    SellerResponseDTO getSeller(Long id);

    SellerResponseDTO getSellerByUserId(Long userId);

    SellerResponseDTO updateSeller(Long id, SellerRequestDTO sellerRequestDTO);

    SellerResponseDTO create(UserRequestDTO userRequestDTO, SellerRequestDTO sellerRequestDTO, MultipartFile file);

    PageResponseDTO<SellerResponseDTO> getAllSellers(Pageable pageable);


    List<SellerResponseDTO> getAllSellers();

    List<SellerSummaryDTO> getSellerSummaries();

    PageResponseDTO<SellerSummaryDTO> getSellerSummaries(Pageable pageable);

    void deleteSeller(Long id);

    SellerResponseDTO updateSellerImage(Long id, MultipartFile file);

    SellerResponseDTO updateSellerAddress(Long id, Long addressId);

    List<SellerResponseDTO> getSellersByCity(String city);

    boolean existsById(Long id);

    boolean existsByNbLicense(String nbLicense);

    long countSellers();
}