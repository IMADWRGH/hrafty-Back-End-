package com.hrafty.web_app.dto;


import com.hrafty.web_app.entities.User;

import java.nio.file.attribute.UserPrincipal;

public record CustomerDTO(Long id,String image,String sexe,String phone,Long userId) {
}
