package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.AddressDTO;

import java.util.Set;

public interface AddressService {

   AddressDTO getAddressSeller(Long id);
   Set<String> getAllCities();

   AddressDTO updateAddress(Long id,AddressDTO updateAddressDTO);
}
