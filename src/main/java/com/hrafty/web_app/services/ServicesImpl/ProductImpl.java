package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.ProductRepository;
import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.dto.ProductDTO;
import com.hrafty.web_app.entities.Product;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.exception.InvalidRequest;
import com.hrafty.web_app.exception.ProductNotFoundException;
import com.hrafty.web_app.mapper.ProductMapper;
import com.hrafty.web_app.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductImpl implements ProductService {
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
        Seller seller = sellerRepository.findById(productDTO.sellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        Product product =productMapper.toEntity(productDTO);
        product.setSeller(seller);
        Product saveProduct=productRepository.save(product);
        return productMapper.toDTO(saveProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products =productRepository.findAll();
        List<ProductDTO> productDTOList=new ArrayList<>();
        for(Product product:products){
                productDTOList.add(productMapper.toDTO(product));
        }
        return productDTOList;
    }

    @Override
    public List<ProductDTO> getAllProducts(Long id) {
        List<Product> products =productRepository.findAllBySellerId(id);
        List<ProductDTO> productDTOList=new ArrayList<>();
        for(Product product:products){
            productDTOList.add(productMapper.toDTO(product));
        }
        return productDTOList;
    }

    @Override
    public Set<String> getAllCategories() {
        return productRepository.findAllCategories();
    }

    @Override
    public void updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new InvalidRequest("product not found"));
        if (product != null) {
            Product updatedProduct = productMapper.toEntity(productDTO);
            updatedProduct.setId(product.getId());
            updatedProduct = productRepository.save(updatedProduct);
            productMapper.toDTO(updatedProduct);
        }

    }

    @Override
    public ProductDTO getProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productMapper.toDTO(productOptional.get());
        }
        throw new ProductNotFoundException("Product not found for id: ",new Throwable());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);

    }

    @Override
    public List<ProductDTO> getAllProducts(String category) {
       List<Product> products= productRepository.findAllByCategory(category);
       List<ProductDTO> productDTOS=new ArrayList<>();
       for(Product product:products){
           productDTOS.add(productMapper.toDTO(product));
       }
       return productDTOS;
    }
}
