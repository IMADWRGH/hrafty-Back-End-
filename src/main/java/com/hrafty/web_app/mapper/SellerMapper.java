package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.common.AddressDTO;
import com.hrafty.web_app.dto.request.SellerRequestDTO;
import com.hrafty.web_app.dto.response.SellerResponseDTO;
import com.hrafty.web_app.dto.response.SellerSummaryDTO;
import com.hrafty.web_app.entities.Address;
import com.hrafty.web_app.entities.Seller;
import com.hrafty.web_app.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, AddressMapper.class})
public interface SellerMapper {

    @Named("mapSellerById")
    default Seller mapSellerById(Long id) {
        if (id == null) return null;
        Seller seller = new Seller();
        seller.setId(id);
        return seller;
    }

    @Named("mapSellerToId")
    default Long mapSellerToId(Seller seller) {
        return seller == null ? null : seller.getId();
    }

    @Named("mapUserIdToUserForSeller")
    default User mapUserIdToUserForSeller(Long userId) {
        if (userId == null) return null;
        User user = new User();
        user.setId(userId);
        return user;
    }

    // FIX 1: Uncommented calculateProductCount and calculateServiceCount.
    //
    //   These methods were COMMENTED OUT in the original code but still referenced
    //   in @Mapping annotations on toResponseDTO:
    //     @Mapping(source="products", target="productCount", qualifiedByName="calculateProductCount")
    //     @Mapping(source="services", target="serviceCount", qualifiedByName="calculateServiceCount")
    //
    //   MapStruct generates code at compile time and looks up @Named methods.
    //   When the method doesn't exist, the build fails with:
    //     "No property named 'calculateProductCount' exists in source parameter(s)"
    //
    //   Solution: Uncomment the methods. They're simple null-safe size() calls.
    @Named("calculateProductCount")
    default Integer calculateProductCount(List products) {
        return products != null ? products.size() : 0;
    }

    @Named("calculateServiceCount")
    default Integer calculateServiceCount(List services) {
        return services != null ? services.size() : 0;
    }

    // FIX 2: Added @Mapping(source = "nbLicense", target = "nb_license")
    //
    //   The DTO record accessor is nbLicense() (camelCase).
    //   The Seller entity field is nb_license with setter setNb_license() (snake_case).
    //   MapStruct matches by property name — "nbLicense" != "nb_license" — so
    //   it would NEVER auto-map this field. nb_license would always be null in DB.
    //
    //   The explicit mapping tells MapStruct: read nbLicense from DTO, write to nb_license.
    @Mapping(target = "id",         ignore = true)
    @Mapping(source = "nbLicense",  target = "nb_license")    // FIX 2: was missing
    @Mapping(source = "userId",     target = "user", qualifiedByName = "mapUserIdToUserForSeller")
    @Mapping(source = "address",    target = "address")
    @Mapping(target = "services",   ignore = true)
    @Mapping(target = "products",   ignore = true)
    Seller toEntity(SellerRequestDTO dto);

    @Mapping(source = "user.id",      target = "userId")
    @Mapping(source = "user.email",   target = "userEmail")
    @Mapping(source = "address",      target = "address")
    @Mapping(source = "products",     target = "productCount", qualifiedByName = "calculateProductCount")
    @Mapping(source = "services",     target = "serviceCount", qualifiedByName = "calculateServiceCount")
    SellerResponseDTO toResponseDTO(Seller entity);

    // address.name_city → cityName: Address getter is getName_city() → property name_city
    @Mapping(source = "address.name_city", target = "cityName")
    SellerSummaryDTO toSummaryDTO(Seller entity);
}