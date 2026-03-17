package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.request.ProductRequestDTO;
import com.hrafty.web_app.dto.response.ProductResponseDTO;
import com.hrafty.web_app.dto.response.ProductSummaryDTO;
import com.hrafty.web_app.entities.Image;
import com.hrafty.web_app.entities.Product;
import com.hrafty.web_app.entities.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {SellerMapper.class, ImageMapper.class})
public interface ProductMapper {

    @Named("mapProductById")
    default Product mapProductById(Long id) {
        if (id == null) return null;
        Product product = new Product();
        product.setId(id);
        return product;
    }

    @Named("mapProductToId")
    default Long mapProductToId(Product product) {
        return product == null ? null : product.getId();
    }

    @Named("mapSellerIdToSeller")
    default Seller mapSellerIdToSeller(Long sellerId) {
        if (sellerId == null) return null;
        Seller seller = new Seller();
        seller.setId(sellerId);
        return seller;
    }

    @Named("getPrimaryImageUrl")
    default String getPrimaryImageUrl(List<Image> images) {
        if (images == null || images.isEmpty()) return null;
        return images.stream()
                .filter(img -> Boolean.TRUE.equals(img.getPrimary()))
                .findFirst()
                .map(Image::getUrl)
                .orElse(images.get(0).getUrl()); // fallback to first image
    }

    @Mapping(target = "id",     ignore = true)
    @Mapping(target = "seller", source = "sellerId", qualifiedByName = "mapSellerIdToSeller")
    @Mapping(target = "images", ignore = true)
    Product toEntity(ProductRequestDTO dto);

    // FIX 1: Added @Mapping(target = "createdAt", ignore = true) and updatedAt.
    //   Product does NOT extend Auditable — it has no createdAt/updatedAt fields.
    //   Without these ignores, MapStruct throws compile errors:
    //     "No property named 'createdAt' exists in source parameter(s)"
    //   If you want timestamps on products, add "Product extends Auditable"
    //   and run the DB migration ALTER TABLE product ADD COLUMN created_at, updated_at.
    @Mapping(source = "seller.id",             target = "sellerId")
    @Mapping(source = "seller.user.fullName",  target = "sellerName")
    @Mapping(target = "createdAt",             ignore = true)  // FIX: Product has no Auditable
    @Mapping(target = "updatedAt",             ignore = true)  // FIX: Product has no Auditable
    ProductResponseDTO toResponseDTO(Product entity);

    // FIX 2: Old code had @Mapping(source = "seller.id", target = "id")
    //   This mapped the SELLER'S database ID into the product summary's "id" field.
    //   The product summary's id should be the PRODUCT's own id.
    //   The fix is to simply remove that wrong mapping — MapStruct auto-maps
    //   "id" → "id" by name convention, so no annotation needed at all.
    @Mapping(source = "images", target = "primaryImage", qualifiedByName = "getPrimaryImageUrl")
    // id auto-maps: Product.id → ProductSummaryDTO.id  (same name, no annotation needed)
    // name, price, category also auto-map by name
    ProductSummaryDTO toSummaryDTO(Product entity);
}