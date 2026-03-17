package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.request.UserRequestDTO;
import com.hrafty.web_app.dto.request.UserUpdateDTO;
import com.hrafty.web_app.dto.response.UserResponseDTO;
import com.hrafty.web_app.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Named("mapUserById")
    default User mapUserById(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    @Named("mapUserToId")
    default Long mapUserToId(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "customer", ignore = true)
    User toEntity(UserRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "customer", ignore = true)
    User toEntity(UserUpdateDTO dto);

    UserResponseDTO toResponseDTO(User entity);
}