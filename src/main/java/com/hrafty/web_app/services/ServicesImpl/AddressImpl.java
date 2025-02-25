package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.AddressRepository;
import com.hrafty.web_app.dto.AddressDTO;
import com.hrafty.web_app.entities.Address;
import com.hrafty.web_app.mapper.AddressMapper;
import com.hrafty.web_app.services.AddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AddressImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public AddressImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    @Override
    public AddressDTO getAddressSeller(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));
        return addressMapper.toDTO(address);
    }
    @Override
    public Set<String> getAllCities() {
        return addressRepository.findAllDistinctCityNames();
    }

    @Override
    public AddressDTO updateAddress(Long id, AddressDTO updateAddressDTO) {
        Address address=addressRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Address not found"));
        if (address!=null){

        }
        return null;
    }
}
