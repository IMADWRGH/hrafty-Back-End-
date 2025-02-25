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
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllCategories());
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

    @GetMapping(path = "/services")
    public ResponseEntity<List<ServiceDTO>> getAllServiceByCities() {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllServices());
    }

    @GetMapping(path = "/services/city/{city}/category/{category}")
    public ResponseEntity<List<ServiceDTO>> getAllServiceByCityAndCategory(
            @PathVariable("city") String city,
            @PathVariable("category") String category) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllServicesByCityAndCategory(city, category));
    }

    @GetMapping(path = "/services/category/{category}")
    public ResponseEntity<List<ServiceDTO>> getAllServiceByCategory(
            @PathVariable("category") String category) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllServicesCategory(category));
    }

    @GetMapping(path = "/services/city/{city}")
    public ResponseEntity<List<ServiceDTO>> getAllServiceByCity(
            @PathVariable("city") String city) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllServicesCity(city));
    }



}
