package com.hrafty.web_app.dto.common;

import java.util.List;

public record PageResponseDTO<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean last,
        boolean first
) {}
