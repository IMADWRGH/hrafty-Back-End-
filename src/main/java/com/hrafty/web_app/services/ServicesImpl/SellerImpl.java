package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.AddressRepository;
import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.Repository.UserRepository;
import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.dto.UserDTO;
import com.hrafty.web_app.entities.Address;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.entities.User;
import com.hrafty.web_app.exception.EmailAlreadyExistsException;
import com.hrafty.web_app.mapper.AddressMapper;
import com.hrafty.web_app.mapper.SellerMapper;
import com.hrafty.web_app.mapper.UserMapper;
import com.hrafty.web_app.services.ImageService;
import com.hrafty.web_app.services.SellerService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class SellerImpl implements SellerService {
    private final SellerMapper sellerMapper;
    private final SellerRepository sellerRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final ImageService imageService;
    public SellerImpl(SellerMapper sellerMapper, SellerRepository sellerRepository, UserMapper userMapper, UserRepository userRepository, PasswordEncoder passwordEncoder, AddressRepository addressRepository, AddressMapper addressMapper, ImageService imageService) {
        this.sellerMapper = sellerMapper;
        this.sellerRepository = sellerRepository;
        this.userMapper = userMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.imageService = imageService;
    }

    @Override
    public SellerDTO getSeller(Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with id " + id));
        SellerDTO sellerDTO = sellerMapper.toDTO(seller);
        String imageURL = sellerDTO.imageURL();
        if (imageURL != null && !imageURL.isEmpty()) {
            String tokenizedImageUrl = imageService.getImageUrlWithToken(imageURL);
            sellerDTO = new SellerDTO(
                    sellerDTO.id(),
                    sellerDTO.nb_license(),
                    tokenizedImageUrl,
                    sellerDTO.sexe(),
                    sellerDTO.phone(),
                    sellerDTO.site(),
                    sellerDTO.userId(),
                    sellerDTO.addressId()
            );
        }
        return sellerDTO;
    }

    @Override
    public SellerDTO updateSeller(SellerDTO sellerDTO, Long id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found with id " + id));
        sellerMapper.toEntity(sellerDTO);
        Seller updatedSeller = sellerRepository.save(seller);
        return sellerMapper.toDTO(updatedSeller);
    }

    @Override
    public SellerDTO create(UserDTO userDTO, SellerDTO sellerDTO, MultipartFile file) {
        if (userRepository.existsByEmail(userDTO.email())){
            throw new EmailAlreadyExistsException("Email already registered: " + userDTO.email());
        }
        String imageUrl = null;
        if (file != null && !file.isEmpty()) {
            imageUrl = imageService.uploadFile(file);
        }
        SellerDTO sellerDTOWithImage = new SellerDTO(
                null,
                sellerDTO.nb_license(),
                imageUrl,
                sellerDTO.sexe(),
                sellerDTO.phone(),
                sellerDTO.site(),
                null,
                sellerDTO.addressId()
        );
        User userToSave = userMapper.toEntity(userDTO);
        userToSave.setPassword(passwordEncoder.encode(userDTO.password()));
        User savedUser = userRepository.save(userToSave);
        Address savedAddress = addressRepository.save(addressMapper.toEntity(sellerDTOWithImage.addressId()));
        Seller sellerToSave = sellerMapper.toEntity(sellerDTOWithImage);
        sellerToSave.setUser(savedUser);
        sellerToSave.setAddress(savedAddress);
        Seller savedSeller = sellerRepository.save(sellerToSave);
        return sellerMapper.toDTO(savedSeller);
    }

    @Override
    public Page<SellerDTO> getAllSellers(Pageable pageable) {
      return  sellerRepository.findAll(pageable).map(sellerMapper::toDTO);

    }

}
