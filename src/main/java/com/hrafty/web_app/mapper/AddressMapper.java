package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.AddressDTO;
import com.hrafty.web_app.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",uses = {SellerMapper.class})
public interface AddressMapper {

    AddressMapper INSTANCE = Mappers.getMapper(AddressMapper.class);

    default Address map(Long id) {
        if (id == null) {
            return null;
        }
        Address address = new Address();
        address.setId(id);
        return address;
    }

    default Long map(Address address) {
        if (address == null) {
            return null;
        }
        return address.getId();
    }
    @Mapping(source = "shop_number", target = "shop_number")
    @Mapping(source = "name_city", target = "name_city")
    @Mapping(source = "name_regional", target = "name_regional")
    AddressDTO toDTO(Address entity);

    @Mapping(source = "shop_number", target = "shop_number")
    @Mapping(source = "name_city", target = "name_city")
    @Mapping(source = "name_regional", target = "name_regional")
    Address toEntity(AddressDTO dto);
}
