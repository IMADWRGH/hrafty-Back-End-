package com.hrafty.web_app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.services.SellerService;
import com.hrafty.web_app.services.ServiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/seller")
public class SellerController {
    private  final ServiceService service;
    private final SellerService seller;
    private final SellerService sellerService;

    public SellerController(ServiceService service, SellerService seller, SellerService sellerService) {
        this.service = service;
        this.seller = seller;
        this.sellerService = sellerService;
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<SellerDTO> getSeller(@PathVariable("id")Long id){
        SellerDTO sellerDTO=seller.getSeller(id);
        return ResponseEntity.status(HttpStatus.OK).body(sellerDTO);
    }

    @PostMapping(path = "/add",consumes =  MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<ServiceDTO> createService(@RequestPart("service") String servicejson,
                                                    @ModelAttribute("file") List<MultipartFile> files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ServiceDTO serviceDTO = objectMapper.readValue(servicejson, ServiceDTO.class);
        ServiceDTO createService= service.create(serviceDTO,files);
        return ResponseEntity.status(HttpStatus.CREATED).body(createService);
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

    @GetMapping
    public Page<SellerDTO> getSellers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sellerService.getAllSellers(pageable);
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
