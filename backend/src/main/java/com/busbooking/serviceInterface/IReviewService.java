package com.busbooking.serviceInterface;

import java.util.List;

import com.busbooking.dto.ReviewRequestDto;
import com.busbooking.dto.ReviewResponseDto;

public interface IReviewService {

    // add a review for a trip
    ReviewResponseDto addReview(ReviewRequestDto dto);

    // get all reviews for a trip
    List<ReviewResponseDto> getReviewsByTrip(Long tripId);

    // get reviews by logged in customer
    List<ReviewResponseDto> getMyReviews();
}