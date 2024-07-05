package com.hrafty.web_app.controller;

import com.hrafty.web_app.dto.ProductDTO;
import com.hrafty.web_app.services.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "api/v1/product")
@RestController
public class ProductController {
    private final Product product;

    public ProductController(Product product) {
        this.product = product;
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id")Long id){
        ProductDTO productDTO=product.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @PostMapping(path = "/add")
    public ResponseEntity<ProductDTO> createService(@RequestBody ProductDTO productDTO){
        ProductDTO productDTO1= product.create(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(productDTO);
    }
    @GetMapping(path ="/getAll/{id}")
    public ResponseEntity<List<ProductDTO>> getAllProducts(@PathVariable("id") Long id) {
        List<ProductDTO> products = product.getAllProducts(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @GetMapping(path ="/get/{id}")
    public ResponseEntity<ProductDTO> getProducts(@PathVariable("id") Long id) {
        ProductDTO productDTO = product.getProduct(id);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }
    @PutMapping(path = "/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long id,@RequestBody ProductDTO productDTO){
        product.updateProduct(id,productDTO);
        return  ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
        product.deleteProduct(id);
        return new ResponseEntity<>("Product successfully deleted",HttpStatus.OK);
    }
}
