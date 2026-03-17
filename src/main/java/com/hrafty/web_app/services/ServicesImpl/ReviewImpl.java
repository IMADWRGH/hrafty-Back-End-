//package com.hrafty.web_app.services.ServicesImpl;
//
//import com.hrafty.web_app.Repository.CustomerRepository;
//import com.hrafty.web_app.Repository.ReviewsRepository;
//import com.hrafty.web_app.dto.common.PageResponseDTO;
//import com.hrafty.web_app.dto.common.ReviewsDTO;
//import com.hrafty.web_app.dto.request.ReviewRequestDTO;
//import com.hrafty.web_app.dto.response.ReviewResponseDTO;
//import com.hrafty.web_app.entities.Reviews;
//import com.hrafty.web_app.mapper.ReviewsMapper;
//import com.hrafty.web_app.services.ReviewsService;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//public class ReviewImpl implements ReviewsService {
//    private final ReviewsMapper reviewsMapper;
//    private final ReviewsRepository reviewsRepository;
//    private final CustomerRepository customerRepository;
//
//    public ReviewImpl(ReviewsMapper reviewsMapper, ReviewsRepository reviewsRepository, CustomerRepository customerRepository) {
//        this.reviewsMapper = reviewsMapper;
//        this.reviewsRepository = reviewsRepository;
//        this.customerRepository = customerRepository;
//    }
//
//
//
//
//
//    @Override
//    public ReviewsDTO create(ReviewsDTO reviewsDTO) {
//        Reviews  reviews=reviewsMapper.toEntity(reviewsDTO);
//        reviews=reviewsRepository.save(reviews);
//        return reviewsMapper.toDTO(reviews);
//    }
//
//    @Override
//    public ReviewResponseDTO create(ReviewRequestDTO reviewRequestDTO) {
//        return null;
//    }
//
//    @Override
//    public List<ReviewsDTO> getAllReviews() {
//        return null;
//    }
//
//    @Override
//    public PageResponseDTO<ReviewResponseDTO> getAllReviews(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<ReviewResponseDTO> getReviewsByProductId(Long productId) {
//        return List.of();
//    }
//
//    @Override
//    public PageResponseDTO<ReviewResponseDTO> getReviewsByProductId(Long productId, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<ReviewResponseDTO> getReviewsByServiceId(Long serviceId) {
//        return List.of();
//    }
//
//    @Override
//    public PageResponseDTO<ReviewResponseDTO> getReviewsByServiceId(Long serviceId, Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public List<ReviewResponseDTO> getReviewsByCustomerId(Long customerId) {
//        return List.of();
//    }
//
//    @Override
//    public List<ReviewsDTO> getAllReviews(Long id) {
//        return null;
//    }
//
//    @Override
//    public void updateReviews(Long id, ReviewsDTO productDTO) {
//
//    }
//
//    @Override
//    public ReviewsDTO getReview(Long id) {
//        Reviews reviews =reviewsRepository.findById(id)
//                .orElseThrow(()->new EntityNotFoundException("Review not found"));
//        return reviewsMapper.toDTO(reviews);
//    }
//
//    @Override
//    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO) {
//        return null;
//    }
//
//    @Override
//    public void deleteReview(Long id) {
//      reviewsRepository.deleteById(id);
//    }
//
//    @Override
//    public Double getAverageRatingByProductId(Long productId) {
//        return 0.0;
//    }
//
//    @Override
//    public Double getAverageRatingByServiceId(Long serviceId) {
//        return 0.0;
//    }
//
//    @Override
//    public long countReviewsByProductId(Long productId) {
//        return 0;
//    }
//
//    @Override
//    public long countReviewsByServiceId(Long serviceId) {
//        return 0;
//    }
//
//    @Override
//    public boolean existsById(Long id) {
//        return false;
//    }
//
//    @Override
//    public boolean hasCustomerReviewedProduct(Long customerId, Long productId) {
//        return false;
//    }
//
//    @Override
//    public boolean hasCustomerReviewedService(Long customerId, Long serviceId) {
//        return false;
//    }
//
//    @Override
//    public ReviewsDTO updateReview(Long id, ReviewsDTO updatedReviewsDTO) {
//        Reviews reviews=reviewsRepository.findById(id)
//                .orElseThrow(()->new EntityNotFoundException("Reviews not found"));
//        if (reviews !=null){
//            Reviews updateReviews=reviewsMapper.toEntity(updatedReviewsDTO);
//            updateReviews.setId(reviews.getId());
//            updateReviews=reviewsRepository.save(updateReviews);
//            return reviewsMapper.toDTO(updateReviews);
//        }
//        return null;
//    }
//}
