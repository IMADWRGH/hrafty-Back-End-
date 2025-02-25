package com.hrafty.web_app.services.ServicesImpl;

import com.hrafty.web_app.Repository.CustomerRepository;
import com.hrafty.web_app.Repository.ReviewsRepository;
import com.hrafty.web_app.dto.ReviewsDTO;
import com.hrafty.web_app.entities.Reviews;
import com.hrafty.web_app.mapper.ReviewsMapper;
import com.hrafty.web_app.services.ReviewsService;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class ReviewImpl implements ReviewsService {
    private final ReviewsMapper reviewsMapper;
    private final ReviewsRepository reviewsRepository;
    private final CustomerRepository customerRepository;

    public ReviewImpl(ReviewsMapper reviewsMapper, ReviewsRepository reviewsRepository, CustomerRepository customerRepository) {
        this.reviewsMapper = reviewsMapper;
        this.reviewsRepository = reviewsRepository;
        this.customerRepository = customerRepository;
    }





    @Override
    public ReviewsDTO create(ReviewsDTO reviewsDTO) {
        Reviews  reviews=reviewsMapper.toEntity(reviewsDTO);
        reviews=reviewsRepository.save(reviews);
        return reviewsMapper.toDTO(reviews);
    }

    @Override
    public List<ReviewsDTO> getAllReviews() {
        return null;
    }

    @Override
    public List<ReviewsDTO> getAllReviews(Long id) {
        return null;
    }

    @Override
    public void updateReviews(Long id, ReviewsDTO productDTO) {

    }

    @Override
    public ReviewsDTO getReview(Long id) {
        Reviews reviews =reviewsRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Review not found"));
        return reviewsMapper.toDTO(reviews);
    }

    @Override
    public void deleteReview(Long id) {
      reviewsRepository.deleteById(id);
    }

    @Override
    public ReviewsDTO updateReview(Long id, ReviewsDTO updatedReviewsDTO) {
        Reviews reviews=reviewsRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Reviews not found"));
        if (reviews !=null){
            Reviews updateReviews=reviewsMapper.toEntity(updatedReviewsDTO);
            updateReviews.setId(reviews.getId());
            updateReviews=reviewsRepository.save(updateReviews);
            return reviewsMapper.toDTO(updateReviews);
        }
        return null;
    }
}
