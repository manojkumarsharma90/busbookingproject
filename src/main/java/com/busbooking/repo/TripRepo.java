package com.busbooking.repo;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Trip;

@Repository
public interface TripRepo extends JpaRepository<Trip, Long> {

	@Query("SELECT t FROM Trip t WHERE t.route.fromCity = :fromCity " +
			"AND t.route.toCity = :toCity " +
			"AND t.tripDate >= :startOfDay AND t.tripDate < :endOfDay " +
			"AND t.availableSeats > 0")
	List<Trip> searchTrips(@Param("fromCity") String fromCity,
						   @Param("toCity") String toCity,
						   @Param("startOfDay") LocalDateTime startOfDay,
						   @Param("endOfDay") LocalDateTime endOfDay);

	List<Trip> findByRoute_RouteId(Long routeId);

	List<Trip> findByBus_BusId(Long busId);
}
