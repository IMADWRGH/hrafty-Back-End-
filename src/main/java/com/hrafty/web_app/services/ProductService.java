package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.ProductDTO;
import com.hrafty.web_app.entities.Product;

import java.util.List;
import java.util.Set;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getAllProducts(Long id);
    Set<String> getAllCategories();
    void updateProduct(Long id , ProductDTO productDTO);
    ProductDTO getProduct(Long id);
    void  deleteProduct(Long id);

    public List<ProductDTO> getAllProducts(String category);
}
