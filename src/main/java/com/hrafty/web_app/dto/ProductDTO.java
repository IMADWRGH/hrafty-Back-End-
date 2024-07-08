package com.hrafty.web_app.dto;

public record ProductDTO(Long id,String image,String name,String description,double price,String category,Long sellerId) {
}
