package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.AddressRepository;
import com.hrafty.web_app.Repository.CustomerRepository;
import com.hrafty.web_app.dto.common.AddressDTO;
import com.hrafty.web_app.dto.response.AddressResponseDTO;
import com.hrafty.web_app.entities.Address;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.mapper.AddressMapper;
import com.hrafty.web_app.services.AddressService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AddressImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CustomerRepository customerRepository;

    public AddressImpl(AddressRepository addressRepository, AddressMapper addressMapper, CustomerRepository customerRepository) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll().stream()
                .map(addressMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AddressDTO getAddressById(Long id) {
        return addressMapper.toDTO(addressRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public AddressDTO getAddressSeller(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));
        return addressMapper.toDTO(address);
    }

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        validateAddressDTO(addressDTO);
        Address address = addressMapper.toEntity(addressDTO);
        return addressMapper.toDTO(addressRepository.save(address));
    }

    @Override
    public AddressDTO updateAddress(Long id, AddressDTO updateAddressDTO) {
        addressRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        validateAddressDTO(updateAddressDTO);
        Address address = addressMapper.toEntity(updateAddressDTO);
        address.setId(id);
        addressRepository.save(address);
        return addressMapper.toDTO(address);
    }

    @Override
    public Set<String> getAllCities() {
      Set<String> cities=addressRepository.findAllDistinctCityNames();
        return  cities !=null ?cities:new HashSet<>();
    }

    @Override
    public AddressResponseDTO getAddressByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found with id " + customerId));
        return null;
    }

    @Override
    public boolean existsById(Long id) {
        return addressRepository.existsById(id);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Address not found"));
        addressRepository.deleteById(id);
    }


    private void validateAddressDTO(AddressDTO addressDTO) {
        if (addressDTO.street() == null || addressDTO.street().isBlank()) {
            throw new IllegalArgumentException("Street is required");
        }
        if (addressDTO.cityName() == null || addressDTO.cityName().isBlank()) {
            throw new IllegalArgumentException("City name is required");
        }
    }
}
