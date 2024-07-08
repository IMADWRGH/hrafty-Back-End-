package com.hrafty.web_app.dto;

import java.util.List;

public record ReviewsDTO(Long id,String comments,byte rating,CustomerDTO customer,List<ServiceDTO> services) {
}
