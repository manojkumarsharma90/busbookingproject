package com.busbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.busbooking.entity.Passenger;

public interface PassengerRepo extends JpaRepository<Passenger, Integer> {
	List<Passenger> findByPName(String pName);

	List<Passenger> findByPAge(Integer age);

	Passenger findBySeatNo(String seatNo);
	
	@Query("SELECT p FROM Passenger p WHERE p.booking.bookingId =:bId")
	List<Passenger> getPassengersByBookingId(@Param("bId") Integer bId);
	
	@Query("""
			SELECT p.seatNo 
			FROM Passenger p
			WHERE p.booking.trip.tripId = :tripId
			""")
			List<String> findBookedSeatsByTripId(@Param("tripId") Long tripId);

}
