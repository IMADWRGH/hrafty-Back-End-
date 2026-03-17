package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.request.ProductRequestDTO;
import com.hrafty.web_app.dto.response.ProductResponseDTO;
import com.hrafty.web_app.dto.response.ProductSummaryDTO;
import com.hrafty.web_app.dto.common.PageResponseDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface ProductService {

    ProductResponseDTO create(ProductRequestDTO productRequestDTO, List<MultipartFile> files);

    List<ProductResponseDTO> getAllProducts();

    PageResponseDTO<ProductResponseDTO> getAllProducts(Pageable pageable);

    List<ProductResponseDTO> getProductsBySellerId(Long sellerId);

    PageResponseDTO<ProductResponseDTO> getProductsBySellerId(Long sellerId, Pageable pageable);

    Set<String> getAllCategories();

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO, List<MultipartFile> files);

    ProductResponseDTO getProduct(Long id);

    void deleteProduct(Long id);

    List<ProductResponseDTO> getProductsByCategory(String category);

    PageResponseDTO<ProductResponseDTO> getProductsByCategory(String category, Pageable pageable);


    List<ProductSummaryDTO> getProductSummaries();

    PageResponseDTO<ProductSummaryDTO> getProductSummaries(Pageable pageable);

    List<ProductResponseDTO> searchProducts(String keyword);

    List<ProductResponseDTO> getActiveProducts();

    List<ProductResponseDTO> getProductsByPriceRange(double minPrice, double maxPrice);

    ProductResponseDTO updateStock(Long id, Integer newStock);

    ProductResponseDTO toggleActiveStatus(Long id);

    boolean existsById(Long id);

    long countProducts();

    long countProductsBySellerId(Long sellerId);
}