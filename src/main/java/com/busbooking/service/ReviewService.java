package com.busbooking.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.busbooking.dto.BookingMapper;
import com.busbooking.dto.ReviewRequestDto;
import com.busbooking.dto.ReviewResponseDto;
import com.busbooking.entity.Review;
import com.busbooking.entity.Trip;
import com.busbooking.entity.User;
import com.busbooking.exception.ResourceNotFoundException;
import com.busbooking.repository.ReviewRepo;
import com.busbooking.repository.TripRepo;
import com.busbooking.repository.UserRepo;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private TripRepo tripRepo;

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private BookingMapper mapper;


    // add a review for a trip
    public ReviewResponseDto addReview(ReviewRequestDto dto) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getCustomer() == null) {
            throw new com.busbooking.exception.BadRequestException("User does not have a customer profile");
        }

        Trip trip = tripRepo.findById(dto.getTripId())
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + dto.getTripId()));

        Review review = new Review();
        review.setCustomer(user.getCustomer());
        review.setTrip(trip);
        review.setRating(dto.getRating());
        review.setComment(dto.getComment());
        review.setReviewDate(LocalDateTime.now());

        Review sReview= reviewRepo.save(review);
        
        return mapper.mapToReviewResponseDto(sReview);
        
         
    }

    // get all reviews for a trip
    public List<ReviewResponseDto> getReviewsByTrip(Long tripId) {
        List<Review> review= reviewRepo.findByTrip_TripId(tripId);
        
        return review.stream().map(mapper::mapToReviewResponseDto).toList();
    }

    // get reviews by logged in customer
    public List<ReviewResponseDto> getMyReviews() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getCustomer() == null) {
        	throw new com.busbooking.exception.BadRequestException("User does not have a customer profile");        }

        List<Review> review= reviewRepo.findByCustomer_CustomerId(user.getCustomer().getCustomerId());
        
        return review.stream().map(mapper::mapToReviewResponseDto).toList();
        
    }
}
