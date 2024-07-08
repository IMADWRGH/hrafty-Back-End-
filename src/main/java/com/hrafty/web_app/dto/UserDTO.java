package com.hrafty.web_app.dto;

import com.hrafty.web_app.entities.Role;


public record UserDTO(Long id,String fullName,String email,String password,Role role) {
}

