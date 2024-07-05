package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.services.Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/service")
@CrossOrigin(origins = "http://localhost:4200")
public class ServiceController {
    private  final Service service;

    public ServiceController(Service service) {
        this.service = service;
    }


    @GetMapping(path = "/get")
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        List<ServiceDTO> serviceDTOS = service.getAllServices();
        return new ResponseEntity<>(serviceDTOS, HttpStatus.OK);
    }
    @PostMapping(path = "/get/{name}/{type}")
    public ResponseEntity<List<ServiceDTO>> getAllServices(@PathVariable("name")String name,@PathVariable("type") String type) {
        List<ServiceDTO> serviceDTOS = service.getAllServices(name,type);
        return new ResponseEntity<>(serviceDTOS, HttpStatus.OK);
    }
    @PostMapping(path = "/get/{type}")
    public ResponseEntity<List<ServiceDTO>> getAllServices(@PathVariable("type") String type) {
        List<ServiceDTO> serviceDTOS = service.getAllServices(type);
        return new ResponseEntity<>(serviceDTOS, HttpStatus.OK);
    }

    @GetMapping(path = "/allTypes")
    public ResponseEntity<List<String>> getAllType(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllTypes());
    }
}
