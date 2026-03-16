package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.common.PageResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface SearchService {

    PageResponseDTO<Object> globalSearch(String keyword, Pageable pageable);

    List<Object> searchProducts(String keyword);

    List<Object> searchServices(String keyword);

    List<Object> searchSellers(String keyword);

    Map<String, List<Object>> searchAll(String keyword);
}