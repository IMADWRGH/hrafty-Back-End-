package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.request.OrderItemRequestDTO;
import com.hrafty.web_app.dto.response.OrderItemResponseDTO;
import com.hrafty.web_app.entities.OrderItem;
import com.hrafty.web_app.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderItemMapper {

    @Named("mapOrderItemById")
    default OrderItem mapOrderItemById(Long id) {
        if (id == null) {
            return null;
        }
        OrderItem item = new OrderItem();
        item.setId(id);
        return item;
    }

    @Named("mapOrderItemToId")
    default Long mapOrderItemToId(OrderItem item) {
        if (item == null) {
            return null;
        }
        return item.getId();
    }

    @Named("mapProductIdToProductForOrderItem")
    default Product mapProductIdToProductForOrderItem(Long productId) {
        if (productId == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @Named("getPrimaryImageFromProduct")
    default String getPrimaryImageFromProduct(Product product) {
        if (product == null || product.getImages() == null || product.getImages().isEmpty()) {
            return null;
        }
        return product.getImages().get(0).getUrl();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProductIdToProductForOrderItem")
    @Mapping(target = "order", ignore = true)
    @Mapping(target = "totalPrice", ignore = true)
    OrderItem toEntity(OrderItemRequestDTO dto);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product", target = "productImage", qualifiedByName = "getPrimaryImageFromProduct")
    @Mapping(source = "product.price", target = "unitPrice")
    OrderItemResponseDTO toResponseDTO(OrderItem entity);
}