package com.busbooking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Booking;
import com.busbooking.entity.BookingStatus;

import java.util.List;

@Repository
public interface BookingRepo extends JpaRepository<Booking, Long> {

	List<Booking> findByTrip_TripIdAndStatus(Long tripId, BookingStatus status);

	List<Booking> findByTrip_TripId(Long tripId);

	List<Booking> findByCustomer_CustomerIdAndDeletedFalse(Long customerId);

	List<Booking> findByCustomer_CustomerIdAndDeletedFalseOrderByBookingDateDesc(Long customerId);
}
