package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.services.ServicesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/service")
public class ServiceController {
    private  final ServicesService serviceService;

    public ServiceController(ServicesService serviceService) {
        this.serviceService = serviceService;
    }


    @GetMapping(path = "/get")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<ServiceDTO> serviceDTOS = serviceService.getAllServices();
        return new ResponseEntity<>(serviceDTOS, HttpStatus.OK);
    }



}
