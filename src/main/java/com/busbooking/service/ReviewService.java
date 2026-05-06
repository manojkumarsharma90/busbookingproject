package com.busbooking.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.busbooking.dto.ReviewDto;
import com.busbooking.entity.Review;
import com.busbooking.entity.Trip;
import com.busbooking.entity.User;
import com.busbooking.exception.BadRequestException;
import com.busbooking.exception.ResourceNotFoundException;
import com.busbooking.repo.ReviewRepo;
import com.busbooking.repo.TripRepo;
import com.busbooking.repo.UserRepo;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepo reviewRepo;

	@Autowired
	private TripRepo tripRepo;

	@Autowired
	private UserRepo userRepo;


	// add a review for a trip
	public Review addReview(ReviewDto dto) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (user.getCustomer() == null) {
			throw new BadRequestException("User does not have a customer profile");
		}

		Trip trip = tripRepo.findById(dto.getTripId())
				.orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + dto.getTripId()));

		Review review = new Review();
		review.setCustomer(user.getCustomer());
		review.setTrip(trip);
		review.setRating(dto.getRating());
		review.setComment(dto.getComment());
		review.setReviewDate(LocalDateTime.now());

		return reviewRepo.save(review);
	}

	// get all reviews for a trip
	public List<Review> getReviewsByTrip(Long tripId) {
		return reviewRepo.findByTrip_TripId(tripId);
	}

	// get reviews by logged in customer
	public List<Review> getMyReviews() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (user.getCustomer() == null) {
			throw new BadRequestException("User does not have a customer profile");
		}

		return reviewRepo.findByCustomer_CustomerId(user.getCustomer().getCustomerId());
	}
}
