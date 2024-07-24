package com.hrafty.web_app.dto;


import java.util.List;

public record ServiceDTO(Long id,
                         String name,
                         String description,
                         List<ImageDTO> images,
                         double price,
                         String  category,
                         boolean status,
                         Long sellerId,
                         List<ReviewsDTO> reviewsDTOS,
                         Long panelId) {

}
