package com.busbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busbooking.entity.Booking;
import com.busbooking.entity.BookingStatus;

public interface BookingRepo extends JpaRepository<Booking, Long> {

	List<Booking> findByTrip_TripIdAndStatus(Long tripId, BookingStatus status);

	List<Booking> findByTrip_TripId(Long tripId);

	List<Booking> findByCustomer_CustomerIdAndDeletedFalse(Long customerId);

	List<Booking> findByCustomer_CustomerIdAndDeletedFalseOrderByBookingDateDesc(Long customerId);
	
	List<Booking> findByCustomer_CustomerIdAndStatusAndDeletedFalse(
	        Long customerId, BookingStatus status);
}

