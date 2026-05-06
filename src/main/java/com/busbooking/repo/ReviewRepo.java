package com.busbooking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Review;

import java.util.List;

@Repository
public interface ReviewRepo extends JpaRepository<Review, Long> {
	List<Review> findByTrip_TripId(Long tripId);
	List<Review> findByCustomer_CustomerId(Long customerId);
}
