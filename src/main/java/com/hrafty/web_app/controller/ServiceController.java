package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.services.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/service")
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceController {
    private  final ServiceService service;

    public ServiceController(ServiceService service) {
        this.service = service;
    }


    @GetMapping(path = "/get")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<ServiceDTO> serviceDTOS = service.getAllServices();
        return new ResponseEntity<>(serviceDTOS, HttpStatus.OK);
    }
    @PostMapping(path = "/get/{name}/{category}")
    public ResponseEntity<List<ServiceDTO>> getAllServices(@PathVariable("name")String name,@PathVariable("category") String category) {
        List<ServiceDTO> serviceDTOS = service.getAllServices(name,category);
        return new ResponseEntity<>(serviceDTOS, HttpStatus.OK);
    }
    @PostMapping(path = "/get/{category}")
    public ResponseEntity<List<ServiceDTO>> getAllServices(@PathVariable("category") String category) {
        List<ServiceDTO> serviceDTOS = service.getAllServices(category);
        return new ResponseEntity<>(serviceDTOS, HttpStatus.OK);
    }

}
