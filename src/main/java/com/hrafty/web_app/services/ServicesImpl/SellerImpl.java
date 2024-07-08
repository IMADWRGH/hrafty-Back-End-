package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.mapper.SellerMapper;
import com.hrafty.web_app.services.SellerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SellerImpl implements SellerService {
    private final SellerMapper sellerMapper;
    private final SellerRepository sellerRepository;

    public SellerImpl(SellerMapper sellerMapper, SellerRepository sellerRepository) {
        this.sellerMapper = sellerMapper;
        this.sellerRepository = sellerRepository;
    }

    public SellerDTO getSeller(Long id) {
        com.hrafty.web_app.entities.Seller seller = sellerRepository.findByUserId(id);
        return sellerMapper.toDTO(seller);
    }

    @Override
    public SellerDTO updateSeller(SellerDTO sellerDTO, Long id) {
        com.hrafty.web_app.entities.Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with id " + id));
        sellerMapper.toEntity(sellerDTO);
        com.hrafty.web_app.entities.Seller updatedSeller = sellerRepository.save(seller);
        return sellerMapper.toDTO(updatedSeller);
    }

}
