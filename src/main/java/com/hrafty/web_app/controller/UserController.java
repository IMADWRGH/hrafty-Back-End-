package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.ProductDTO;
import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.services.AddressService;
import com.hrafty.web_app.services.ProductService;
import com.hrafty.web_app.services.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final ProductService product;
    private  final ServiceService service;
    private final AddressService address;
    public UserController(ProductService product, ServiceService service, AddressService address) {
        this.product = product;
        this.service = service;
        this.address = address;
    }

    @GetMapping(path ="/products")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = product.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(path = "/service-categories")
    public ResponseEntity<List<String>> getAllServicesCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCatrgories());
    }

    @GetMapping(path = "/product-categories")
    public ResponseEntity<Set<String>> getAllProductsCategories(){
        return ResponseEntity.status(HttpStatus.OK).body(product.getAllCategories());
    }

    @GetMapping(path = "/cities")
    public ResponseEntity<Set<String>> getAllCities(){
        return ResponseEntity.status(HttpStatus.OK).body(address.getAllCities());
    }


    @GetMapping(path = "/products/category/{category}")
    public ResponseEntity<List<ProductDTO>> getAllProduct(@PathVariable String category){
        return ResponseEntity.status(HttpStatus.OK).body(product.getAllProducts(category));
    }

    @PostMapping(path="/service/{city}")
    public ResponseEntity<List<ServiceDTO>> getAllServiceByCities(@PathVariable String city){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllServices(city));
    }
    @PostMapping(path="/service/{city}/{category}")
    public ResponseEntity<List<ServiceDTO>> getAllServiceByCategories(@PathVariable("city") String city,@PathVariable("category") String category){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllServices(category));
    }


}
