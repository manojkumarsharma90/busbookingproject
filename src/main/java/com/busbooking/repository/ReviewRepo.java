package com.busbooking.repository;

import com.busbooking.entity.Review;
import com.busbooking.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Long> {
     List<Review> findByTripIn(List<Trip> trips);
}
