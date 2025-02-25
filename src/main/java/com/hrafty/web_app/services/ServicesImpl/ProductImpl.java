package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.ProductRepository;
import com.hrafty.web_app.Repository.SellerRepository;
import com.hrafty.web_app.dto.ImageDTO;
import com.hrafty.web_app.dto.ProductDTO;
import com.hrafty.web_app.dto.ServiceDTO;
import com.hrafty.web_app.entities.Image;
import com.hrafty.web_app.entities.Product;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.exception.InvalidRequest;
import com.hrafty.web_app.exception.ProductNotFoundException;
import com.hrafty.web_app.mapper.ImageMapper;
import com.hrafty.web_app.mapper.ProductMapper;
import com.hrafty.web_app.services.ImageService;
import com.hrafty.web_app.services.ProductService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductImpl implements ProductService {
    private final ProductMapper productMapper;
    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ImageService imageService;
    private final ImageMapper imageMapper;
    public ProductImpl(ProductMapper productMapper, ProductRepository productRepository, SellerRepository sellerRepository, ImageService imageService, ImageMapper imageMapper) {
        this.productMapper = productMapper;
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.imageService = imageService;
        this.imageMapper = imageMapper;
    }

    @Override
    public ProductDTO create(ProductDTO productDTO, List<MultipartFile> files) {
        Seller seller = sellerRepository.findById(productDTO.sellerId())
                .orElseThrow(() -> new EntityNotFoundException("Seller not found"));
        Product product = productMapper.toEntity(productDTO);
        product.setSeller(seller);
        Product savedProduct = productRepository.save(product);
        List<ImageDTO> imageDTOs = imageService.uploadFiles(files);
        for (ImageDTO imageDTO : imageDTOs) {
            Image image = imageMapper.toEntity(imageDTO);
            image.setProduct(savedProduct);
            savedProduct.addImage(image);
        }
        savedProduct = productRepository.save(savedProduct);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products =productRepository.findAllWithImages();
        return products.stream()
                .map(product -> {
                    ProductDTO dto = productMapper.toDTO(product);
                    List<ImageDTO> imagesWithToken = imageService.getAllImageByProducts();
                    return new ProductDTO(
                            dto.id(),
                            imagesWithToken,
                            dto.name(),
                            dto.description(),
                            dto.price(),
                            dto.category(),
                            dto.sellerId()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductDTO> getAllProducts(Long sellerId) {
        List<Product> products = productRepository.findAllBySellerIdWithImages(sellerId);
        return products.stream()
                .map(product -> {
                    ProductDTO dto = productMapper.toDTO(product);
                    List<ImageDTO> imagesWithToken = imageService.getAllImageByProduct(product.getId());
                    return new ProductDTO(
                            dto.id(),
                            imagesWithToken,
                            dto.name(),
                            dto.description(),
                            dto.price(),
                            dto.category(),
                            dto.sellerId()
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllCategories() {
        return productRepository.findAllCategories();
    }
    

    @Override
    public void updateProduct(Long id, ProductDTO productDTO,List<MultipartFile> files) {
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
            Product product=productOptional.get();
            ProductDTO dto =productMapper.toDTO(product);
            List<ImageDTO> imagesWithToken = imageService.getAllImageByService(product.getId());
            return new ProductDTO(
                    dto.id(),
                    imagesWithToken,
                    dto.name(),
                    dto.description(),
                    dto.price(),
                    dto.category(),
                    dto.sellerId()
            );
        } else {
            throw new ProductNotFoundException("Product not found for id: ",new Throwable());
        }
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
