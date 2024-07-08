package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.ProductDTO;
import com.hrafty.web_app.services.ProductService;
import com.hrafty.web_app.services.ServiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/user")
public class UserController {
    private final ProductService product;
    private  final ServiceService service;
    public UserController(ProductService product, ServiceService service) {
        this.product = product;
        this.service = service;
    }

    @GetMapping(path ="/getAll")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = product.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping(path = "/ServicesCategories")
    public ResponseEntity<List<String>> getAllType(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllTypes());
    }

    @GetMapping(path = "/ProductsCategories")
    public ResponseEntity<List<String>> getAllTypes(){
        return ResponseEntity.status(HttpStatus.OK).body(product.getAllCategories());
    }
}
