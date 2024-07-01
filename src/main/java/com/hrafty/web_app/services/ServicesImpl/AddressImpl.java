package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.AddressRepository;
import com.hrafty.web_app.dto.AddressDTO;
import com.hrafty.web_app.mapper.AddressMapper;
import com.hrafty.web_app.services.Address;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AddressImpl implements Address {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressDTO getAddressSeller(Long id) {
        com.hrafty.web_app.entities.Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));
        return addressMapper.toDTO(address);
    }
}
