package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.services.Seller;
import com.hrafty.web_app.services.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/seller")
public class SellerController {
    private  final Service service;
    private final Seller seller;
    public SellerController(Service service, Seller seller) {
        this.service = service;
        this.seller = seller;
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<SellerDTO> getSeller(@PathVariable("id")Long id){
        SellerDTO sellerDTO=seller.getSeller(id);
        return ResponseEntity.status(HttpStatus.OK).body(sellerDTO);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO){
        ServiceDTO serviceDTO1= service.create(serviceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceDTO1);
    }
    @GetMapping(path ="/getAll/{id}")
    public ResponseEntity<List<ServiceDTO>> getAllServices(@PathVariable("id") Long id) {
        List<ServiceDTO> services = service.getAllServices(id);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }
    @GetMapping(path ="/get/{id}")
    public ResponseEntity<ServiceDTO> getService(@PathVariable("id") Long id) {
        ServiceDTO services = service.getService(id);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable("id") Long id,@RequestBody ServiceDTO serviceDTO){
        service.updateService(id,serviceDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(serviceDTO);
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable("id") Long id){
        service.deleteService(id);
        return new ResponseEntity<>("Service successfully deleted (: ",HttpStatus.OK);
    }
}
