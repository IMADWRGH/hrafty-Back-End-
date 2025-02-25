package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.CustomerDTO;
import com.hrafty.web_app.entities.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CustomerMapper {
    @Mappings({
            @Mapping(source = "user.id", target = "userId"),
    })
    CustomerDTO toDTO(Customer entity);
    @Mappings({
            @Mapping(source = "userId", target = "user"),
    })
    Customer toEntity(CustomerDTO dto);
}