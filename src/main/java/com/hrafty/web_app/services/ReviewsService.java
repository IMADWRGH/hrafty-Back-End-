package com.hrafty.web_app.services;

import com.hrafty.web_app.dto.request.ReviewRequestDTO;
import com.hrafty.web_app.dto.response.ReviewResponseDTO;
import com.hrafty.web_app.dto.common.PageResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewsService {

    ReviewResponseDTO create(ReviewRequestDTO reviewRequestDTO);

    List<ReviewResponseDTO> getAllReviews();

    PageResponseDTO<ReviewResponseDTO> getAllReviews(Pageable pageable);

    List<ReviewResponseDTO> getReviewsByProductId(Long productId);

    PageResponseDTO<ReviewResponseDTO> getReviewsByProductId(Long productId, Pageable pageable);

    List<ReviewResponseDTO> getReviewsByServiceId(Long serviceId);

    PageResponseDTO<ReviewResponseDTO> getReviewsByServiceId(Long serviceId, Pageable pageable);

    List<ReviewResponseDTO> getReviewsByCustomerId(Long customerId);

    ReviewResponseDTO getReview(Long id);

    ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO);

    void deleteReview(Long id);


    Double getAverageRatingByProductId(Long productId);

    Double getAverageRatingByServiceId(Long serviceId);

    long countReviewsByProductId(Long productId);

    long countReviewsByServiceId(Long serviceId);

    boolean existsById(Long id);

    boolean hasCustomerReviewedProduct(Long customerId, Long productId);

    boolean hasCustomerReviewedService(Long customerId, Long serviceId);
}