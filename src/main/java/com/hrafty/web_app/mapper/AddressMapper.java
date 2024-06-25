package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.AddressDTO;
import com.hrafty.web_app.entities.Address;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",uses = {SellerMapper.class,CustomerMapper.class})
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
    AddressDTO toDTO(Address entity);
    Address toEntity(AddressDTO dto);
}
