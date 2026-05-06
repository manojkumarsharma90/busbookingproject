package com.busbooking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Passenger;

import java.util.List;

@Repository
public interface PassengerRepo extends JpaRepository<Passenger, Long> {
	List<Passenger> findByBooking_BookingId(Long bookingId);
}
