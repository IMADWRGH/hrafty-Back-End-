package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.services.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/seller")
public class SellerController {
    private  final Service service;

    public SellerController(Service service) {
        this.service = service;
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

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable("id") Long id,@RequestBody ServiceDTO serviceDTO){
        service.updateService(id,serviceDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(serviceDTO);
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable("id") Long id){
        service.deleteService(id);
        return new ResponseEntity<>("Serive successfully deleted (: ",HttpStatus.OK);
    }
}
