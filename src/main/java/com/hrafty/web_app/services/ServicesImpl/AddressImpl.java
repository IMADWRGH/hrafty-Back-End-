//package com.hrafty.web_app.services.ServicesImpl;
//
//import com.hrafty.web_app.Repository.AddressRepository;
//import com.hrafty.web_app.dto.common.AddressDTO;
//import com.hrafty.web_app.dto.response.AddressResponseDTO;
//import com.hrafty.web_app.entities.Address;
//import com.hrafty.web_app.mapper.AddressMapper;
//import com.hrafty.web_app.services.AddressService;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@Service
//public class AddressImpl implements AddressService {
//    private final AddressRepository addressRepository;
//    private final AddressMapper addressMapper;
//
//    public AddressImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
//        this.addressRepository = addressRepository;
//        this.addressMapper = addressMapper;
//    }
//
//    @Override
//    public List<AddressDTO> getAllAddresses() {
//        return addressRepository.findAll().stream()
//                .map(addressMapper::toDTO)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public AddressDTO getAddressById(Long id) {
//        return null;
//    }
//
//    @Override
//    public AddressDTO getAddressSeller(Long id) {
//        Address address = addressRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Address not found with id " + id));
//        return addressMapper.toDTO(address);
//    }
//
//    @Override
//    public AddressDTO createAddress(AddressDTO addressDTO) {
//        return null;
//    }
//
//    @Override
//    public Set<String> getAllCities() {
//        return addressRepository.findAllDistinctCityNames();
//    }
//
//    @Override
//    public AddressResponseDTO getAddressByCustomerId(Long customerId) {
//        return null;
//    }
//
//    @Override
//    public boolean existsById(Long id) {
//        return false;
//    }
//
//    @Override
//    public AddressDTO updateAddress(Long id, AddressDTO updateAddressDTO) {
//        Address address = addressRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
//
//        addressMapper.updateAddressFromDTO(updateAddressDTO, address);
//        Address updatedAddress = addressRepository.save(address);
//
//        return addressMapper.toDTO(updatedAddress);
//    }
//
//    @Override
//    public void deleteAddress(Long id) {
//
//    }
//}
