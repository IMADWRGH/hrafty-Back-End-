package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.request.UserRequestDTO;
import com.hrafty.web_app.dto.request.UserUpdateDTO;
import com.hrafty.web_app.dto.response.UserResponseDTO;
import com.hrafty.web_app.dto.common.PageResponseDTO;
import com.hrafty.web_app.entities.enums.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserResponseDTO createUser(UserRequestDTO userRequestDTO);

    UserResponseDTO getUserById(Long id);

    UserResponseDTO getUserByEmail(String email);

    List<UserResponseDTO> getAllUsers();

    PageResponseDTO<UserResponseDTO> getAllUsers(Pageable pageable);

    UserResponseDTO updateUser(Long id, UserUpdateDTO userUpdateDTO);

    void deleteUser(Long id);


    List<UserResponseDTO> getUsersByRole(Role role);

    PageResponseDTO<UserResponseDTO> getUsersByRole(Role role, Pageable pageable);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    long countUsers();

    long countUsersByRole(Role role);

    void changeUserRole(Long id, Role newRole);

    void enableUser(Long id);

    void disableUser(Long id);

    void updateLastLogin(Long id);
}