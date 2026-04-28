package com.busbooking.repository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.busbooking.entity.Trip;

@Repository
public interface TripRepo extends JpaRepository<Trip, Long> {
	 List<Trip> findByRouteRouteId(Long routeId);
	 List<Trip> findByTripDate(LocalDateTime tripDate);
}