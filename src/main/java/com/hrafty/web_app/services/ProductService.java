package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ProductService {
    ProductDTO create(ProductDTO productDTO,List<MultipartFile> files);
    List<ProductDTO> getAllProducts();
    List<ProductDTO> getAllProducts(Long id);
    Set<String> getAllCategories();
    void updateProduct(Long id , ProductDTO productDT,List<MultipartFile> files);
    ProductDTO getProduct(Long id);
    void  deleteProduct(Long id);

    public List<ProductDTO> getAllProducts(String category);
}
