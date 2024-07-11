package com.hrafty.web_app.dto;

import java.util.List;

public record PanelDTO(Long id,Long customerId,List<Long> services) {
}
