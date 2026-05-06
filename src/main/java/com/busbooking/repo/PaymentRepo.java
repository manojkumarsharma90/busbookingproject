package com.busbooking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Payment;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepo extends JpaRepository<Payment, Long> {
	Optional<Payment> findByBooking_BookingId(Long bookingId);
	List<Payment> findByCustomer_CustomerId(Long customerId);
}
