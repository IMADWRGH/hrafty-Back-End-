package com.hrafty.web_app.dto.common;

public record PanelServiceDTO(
                              Long id,
                              String name,
                              double price,
                              String primaryImageUrl,
                              boolean status) {
}
