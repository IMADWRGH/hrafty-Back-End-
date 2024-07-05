package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.ProductDTO;

import java.util.List;

public interface Product {
    ProductDTO create(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getAllProducts(Long id);
    void updateProduct(Long id , ProductDTO productDTO);
    ProductDTO getProduct(Long id);
    void  deleteProduct(Long id);
}
