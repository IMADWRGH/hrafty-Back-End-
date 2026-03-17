package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.request.ReviewRequestDTO;
import com.hrafty.web_app.dto.response.ReviewResponseDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.Product;
import com.hrafty.web_app.entities.Reviews;
import com.hrafty.web_app.entities.Service;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ReviewsMapper {

    @Named("mapReviewById")
    default Reviews mapReviewById(Long id) {
        if (id == null) {
            return null;
        }
        Reviews review = new Reviews();
        review.setId(id);
        return review;
    }

    @Named("mapReviewToId")
    default Long mapReviewToId(Reviews review) {
        if (review == null) {
            return null;
        }
        return review.getId();
    }

    @Named("mapCustomerIdToCustomerForReview")
    default Customer mapCustomerIdToCustomerForReview(Long customerId) {
        if (customerId == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }

    @Named("mapServiceIdToServiceForReview")
    default Service mapServiceIdToServiceForReview(Long serviceId) {
        if (serviceId == null) {
            return null;
        }
        Service service = new Service();
        service.setId(serviceId);
        return service;
    }

    @Named("mapProductIdToProductForReview")
    default Product mapProductIdToProductForReview(Long productId) {
        if (productId == null) {
            return null;
        }
        Product product = new Product();
        product.setId(productId);
        return product;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "customer", source = "customerId", qualifiedByName = "mapCustomerIdToCustomerForReview")
    @Mapping(target = "service", source = "serviceId", qualifiedByName = "mapServiceIdToServiceForReview")
    @Mapping(target = "product", source = "productId", qualifiedByName = "mapProductIdToProductForReview")
    Reviews toEntity(ReviewRequestDTO dto);

    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "customer.user.fullName", target = "customerName")
    @Mapping(source = "customer.imageURL", target = "customerImageURL")
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "service.name", target = "serviceName")
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    ReviewResponseDTO toResponseDTO(Reviews entity);
}