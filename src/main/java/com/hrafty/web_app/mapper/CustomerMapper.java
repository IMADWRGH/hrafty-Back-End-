package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.request.CustomerRequestDTO;
import com.hrafty.web_app.dto.response.CustomerResponseDTO;
import com.hrafty.web_app.entities.Customer;
import com.hrafty.web_app.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface CustomerMapper {

    @Named("mapCustomerById")
    default Customer mapCustomerById(Long id) {
        if (id == null) {
            return null;
        }
        Customer customer = new Customer();
        customer.setId(id);
        return customer;
    }

    @Named("mapCustomerToId")
    default Long mapCustomerToId(Customer customer) {
        if (customer == null) {
            return null;
        }
        return customer.getId();
    }

    @Named("mapUserIdToUser")
    default User mapUserIdToUser(Long userId) {
        if (userId == null) {
            return null;
        }
        User user = new User();
        user.setId(userId);
        return user;
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "userId", qualifiedByName = "mapUserIdToUser")
    @Mapping(target = "panel", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    Customer toEntity(CustomerRequestDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.fullName", target = "userFullName")
    @Mapping(source = "user.email", target = "userEmail")
    @Mapping(source = "panel.id", target = "panelId")
    CustomerResponseDTO toResponseDTO(Customer entity);
}