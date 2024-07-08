package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.AddressDTO;
import com.hrafty.web_app.services.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/address")
public class AddressController {

    private  final AddressService address;

    public AddressController(AddressService address) {
        this.address = address;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<AddressDTO> getSellerAddress(@PathVariable("id")Long id){
        AddressDTO addressSeller=address.getAddressSeller(id);
        return ResponseEntity.status(HttpStatus.OK).body(addressSeller);
    }
    @GetMapping(path = "/list")
    public ResponseEntity<List<String>> getAllCities(){
        return ResponseEntity.status(HttpStatus.OK).body(address.getAllCities());
    }
}
