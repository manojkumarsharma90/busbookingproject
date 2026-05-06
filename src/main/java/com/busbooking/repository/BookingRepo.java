package com.busbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busbooking.entity.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long> {

}
