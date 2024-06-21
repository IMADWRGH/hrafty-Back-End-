package com.hrafty.web_app.mapper;

import com.hrafty.web_app.dto.ReviewsDTO;
import com.hrafty.web_app.entities.Reviews;
import com.hrafty.web_app.entities.Seller;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ReviewsMapper {
    ReviewsMapper INSTANCE = Mappers.getMapper(ReviewsMapper.class);

    default Reviews map(Long id) {
        if (id == null) {
            return null;
        }
        Reviews reviews = new Reviews();
        reviews.setId(id);
        return reviews;
    }

    default Long map(Reviews reviews) {
        if (reviews == null) {
            return null;
        }
        return reviews.getId();
    }

    ReviewsDTO toDTO(Reviews entity);

    Reviews toEntity(ReviewsDTO dto);
}
