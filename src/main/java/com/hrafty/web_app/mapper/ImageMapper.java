package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.ImageDTO;
import com.hrafty.web_app.entities.Image;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface ImageMapper {
    ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);

    default Image map(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }

    default Long map(Image image) {
        if (image == null) {
            return null;
        }
        return image.getId();
    }


    ImageDTO toDTO(Image entity);


    Image toEntity(ImageDTO dto);
}
