package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.AddressDTO;

import java.util.List;
import java.util.Set;

public interface AddressService {

   List<AddressDTO> getAllAddresses();
   AddressDTO getAddressById(Long id);
   AddressDTO getAddressSeller(Long id);
   AddressDTO createAddress(AddressDTO addressDTO);
   AddressDTO updateAddress(Long id, AddressDTO updateAddressDTO);
   void deleteAddress(Long id);
   Set<String> getAllCities();
}
