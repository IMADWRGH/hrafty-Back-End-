package com.hrafty.web_app.dto;

import java.util.List;

public record PanelDTO(Long id,CustomerDTO customer,List<Long> services) {
}
