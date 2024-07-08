package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.AddressDTO;

import java.util.List;

public interface AddressService {

   AddressDTO getAddressSeller(Long id);
   List<String> getAllCities();
}
