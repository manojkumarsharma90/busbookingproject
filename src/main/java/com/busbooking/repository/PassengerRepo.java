package com.busbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.busbooking.entity.Passenger;

public interface PassengerRepo extends JpaRepository<Passenger, Integer> {
	List<Passenger> findByBooking_BookingId(Long bookingId);
}
