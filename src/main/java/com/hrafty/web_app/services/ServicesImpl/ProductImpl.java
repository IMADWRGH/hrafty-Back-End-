package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.ProductRepository;
import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.dto.ProductDTO;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.exception.InvalidRequest;
import com.hrafty.web_app.exception.ServiceNotFoundException;
import com.hrafty.web_app.mapper.ProductMapper;
import com.hrafty.web_app.services.Product;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class ProductImpl implements Product {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    public ProductImpl(ProductMapper productMapper, ProductRepository productRepository, SellerRepository sellerRepository) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Override
    public ProductDTO create(ProductDTO productDTO) {
        Seller seller = sellerRepository.findById(productDTO.getSellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        com.hrafty.web_app.entities.Product product =productMapper.toEntity(productDTO);
        product.setSeller(seller);
        com.hrafty.web_app.entities.Product saveProduct=productRepository.save(product);
        return productMapper.toDTO(saveProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<com.hrafty.web_app.entities.Product> products =productRepository.findAll();
        List<ProductDTO> productDTOList=new ArrayList<>();
        for(com.hrafty.web_app.entities.Product product:products){
                productDTOList.add(productMapper.toDTO(product));
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getAllProducts(Long id) {
        List<com.hrafty.web_app.entities.Product> products =productRepository.findAllBySellerId(id);
        List<ProductDTO> productDTOList=new ArrayList<>();
        for(com.hrafty.web_app.entities.Product product:products){
            productDTOList.add(productMapper.toDTO(product));
        }
        return productDTOList;
    }

    @Override
    public void updateProduct(Long id, ProductDTO productDTO) {
        com.hrafty.web_app.entities.Product product = productRepository.findById(id)
                .orElseThrow(() -> new InvalidRequest("product not found"));
        if (product != null) {
            com.hrafty.web_app.entities.Product updatedProduct = productMapper.toEntity(productDTO);
            updatedProduct.setId(product.getId());
            updatedProduct = productRepository.save(updatedProduct);
            productMapper.toDTO(updatedProduct);
        }

    }

    @Override
    public ProductDTO getProduct(Long id) {
        Optional<com.hrafty.web_app.entities.Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            ProductDTO productDTO = productMapper.toDTO(productOptional.get());
            return productDTO;
        }
        throw new ServiceNotFoundException("Product not found for id: ",new Throwable());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);

    }


}
