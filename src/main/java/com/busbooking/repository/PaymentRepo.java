package com.busbooking.repository;

import com.busbooking.entity.Booking;
import com.busbooking.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;

public interface PaymentRepo extends JpaRepository<Payment, Long> {
     List<Payment> findByCustomerCustomerId(Long customerId);
     Optional<Payment> findByBookingBookingId(Long bookingId);
     List<Payment> findByBookingIn(List<Booking> bookings);
}
