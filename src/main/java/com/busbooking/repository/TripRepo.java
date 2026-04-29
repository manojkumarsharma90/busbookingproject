package com.busbooking.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Trip;

@Repository
public interface TripRepo extends JpaRepository<Trip, Long> {

	@Query("SELECT t FROM Trip t WHERE LOWER(t.route.fromCity) = LOWER(:fromCity) "
			+ "AND LOWER(t.route.toCity) = LOWER(:toCity) " + "AND t.tripDate = :date " + "AND t.availableSeats > 0")
	List<Trip> searchTrips(@Param("fromCity") String fromCity, @Param("toCity") String toCity,
			@Param("date") LocalDate date);

	List<Trip> findByRoute_RouteId(Long routeId);

	List<Trip> findByBus_BusId(Long busId);
	List<Trip> findByAvailableSeatsGreaterThan(Integer seats);
}
