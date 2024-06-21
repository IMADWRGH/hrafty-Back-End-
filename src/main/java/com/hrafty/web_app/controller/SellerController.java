package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.services.ServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/seller")
public class SellerController {
    private  final ServicesService serviceService;

    public SellerController(ServicesService serviceService) {
        this.serviceService = serviceService;
    }


    @PostMapping(path = "/add")
    public ResponseEntity<ServiceDTO> createService(@RequestBody ServiceDTO serviceDTO){
        ServiceDTO serviceDTO1= serviceService.create(serviceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceDTO1);
    }

    @GetMapping(path ="/getAll/{id}")
    public ResponseEntity<List<ServiceDTO>> getAllServices(@PathVariable("id") Long id) {
        List<ServiceDTO> services = serviceService.getAllServices(id);
        return new ResponseEntity<>(services, HttpStatus.OK);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ServiceDTO> updateService(@PathVariable("id") Long id,@RequestBody ServiceDTO serviceDTO){
        serviceService.updateService(id,serviceDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(serviceDTO);
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteService(@PathVariable("id") Long id){
        serviceService.deleteService(id);
        return new ResponseEntity<>("Serive successfully deleted (: ",HttpStatus.OK);
    }
}
