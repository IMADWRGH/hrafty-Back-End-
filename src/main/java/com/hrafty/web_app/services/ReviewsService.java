package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.ReviewsDTO;

import java.util.List;

public interface ReviewsService {
    ReviewsDTO create(ReviewsDTO reviewsDTO);
    List<ReviewsDTO> getAllReviews();
    List<ReviewsDTO> getAllReviews(Long id);
    void updateReviews(Long id , ReviewsDTO productDTO);
    ReviewsDTO getReview(Long id);
    void  deleteReview(Long id);

    ReviewsDTO updateReview(Long id,ReviewsDTO updatedReviewsDTO);
}
