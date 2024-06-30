package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.ProductDTO;
import com.hrafty.web_app.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {SellerMapper.class})
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    default Product map(Long id) {
        if (id == null) {
            return null;
        }
        Product product = new Product();
        product.setId(id);
        return product;
    }

    default Long map(Product product) {
        if (product == null) {
            return null;
        }
        return product.getId();
    }

    @Mappings({
            @Mapping(source = "seller.id", target = "sellerId")
    })
    ProductDTO toDTO(Product entity);

    @Mappings({
            @Mapping(source = "sellerId", target = "seller")
    })
    Product toEntity(ProductDTO dto);
}
