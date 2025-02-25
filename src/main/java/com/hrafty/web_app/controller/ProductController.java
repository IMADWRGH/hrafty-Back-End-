package com.hrafty.web_app.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrafty.web_app.dto.ProductDTO;
import com.hrafty.web_app.dto.UserDTO;
import com.hrafty.web_app.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Set;

@RequestMapping(path = "api/v1/product")
@RestController
public class ProductController {
    private final ProductService product;

    public ProductController(ProductService product) {
        this.product = product;
    }


    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable("id")Long id){
        ProductDTO productDTO=product.getProduct(id);
        return ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }

    @PostMapping(path = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> createProduct(@RequestPart("product") String productDTOString,
                                                    @ModelAttribute("files") List<MultipartFile> files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productDTOString, ProductDTO.class);
        ProductDTO createdProduct = product.create(productDTO, files);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
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
    @PutMapping(path = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") Long id,
                                                    @RequestBody String productjson,
                                                    @ModelAttribute("files") List<MultipartFile> files) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDTO productDTO = objectMapper.readValue(productjson, ProductDTO.class);
        product.updateProduct(id,productDTO,files);
        return  ResponseEntity.status(HttpStatus.OK).body(productDTO);
    }
    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id){
        product.deleteProduct(id);
        return new ResponseEntity<>("Product successfully deleted",HttpStatus.OK);
    }
}
