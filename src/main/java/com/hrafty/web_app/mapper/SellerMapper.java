package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.SellerDTO;
import com.hrafty.web_app.entities.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring", uses = {UserMapper.class,AddressMapper.class})
public interface SellerMapper {
    SellerMapper INSTANCE = Mappers.getMapper(SellerMapper.class);

    default Seller map(Long id) {
        if (id == null) {
            return null;
        }
        Seller seller = new Seller();
        seller.setId(id);
        return seller;
    }

    default Long map(Seller seller) {
        if (seller == null) {
            return null;
        }
        return seller.getId();
    }

    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
            @Mapping(source = "address.id", target = "addressId"),
    })
    SellerDTO toDTO(Seller entity);

    @Mappings({
            @Mapping(source = "userId", target = "user")
    })
    Seller toEntity(SellerDTO dto);
}