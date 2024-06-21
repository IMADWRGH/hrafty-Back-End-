package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.UserDTO;
import com.hrafty.web_app.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default User map(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

    default Long map(User user) {
        if (user == null) {
            return null;
        }
        return user.getId();
    }
    UserDTO toDTO(User entity);

    User toEntity(UserDTO dto);
}
