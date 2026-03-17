package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.common.AddressDTO;
import com.hrafty.web_app.dto.response.AddressResponseDTO;
import com.hrafty.web_app.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    @Named("mapAddressById")
    default Address mapAddressById(Long id) {
        if (id == null) return null;
        Address address = new Address();
        address.setId(id);
        return address;
    }

    @Named("mapAddressToId")
    default Long mapAddressToId(Address address) {
        return address == null ? null : address.getId();
    }

    @Mapping(source = "shop_number",   target = "shopNumber")
    @Mapping(source = "name_city",     target = "cityName")
    @Mapping(source = "name_regional", target = "regionalName")
    AddressDTO toDTO(Address entity);

    @Mapping(source = "shopNumber",   target = "shop_number")
    @Mapping(source = "cityName",     target = "name_city")
    @Mapping(source = "regionalName", target = "name_regional")
    Address toEntity(AddressDTO dto);

    // FIX: fullAddress is a computed field (e.g. "street, shop, city").
    // It has NO matching field in the Address entity.
    // Without ignore=true, MapStruct throws: "Unmapped target property: fullAddress"
    @Mapping(source = "shop_number",   target = "shopNumber")
    @Mapping(source = "name_city",     target = "cityName")
    @Mapping(source = "name_regional", target = "regionalName")
    @Mapping(target = "fullAddress",   ignore = true)
    AddressResponseDTO toResponseDTO(Address entity);
}