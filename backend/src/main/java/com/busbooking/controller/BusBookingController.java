package com.busbooking.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.dto.BookingDto;
import com.busbooking.dto.BookingResponseDto;
import com.busbooking.dto.ReviewRequestDto;
import com.busbooking.dto.ReviewResponseDto;
import com.busbooking.dto.TripResponseDto;
import com.busbooking.service.BookingService;
import com.busbooking.service.ReviewService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/bus")
@SecurityRequirement(name = "BearerAuth")
public class BusBookingController {

	@Autowired
	private BookingService bookingService;

	@Autowired
	private ReviewService reviewService;


	
	// SEARCH APIs


	@GetMapping("/schedules")
	public List<TripResponseDto> getSchedules(
			@RequestParam String src,
			@RequestParam String dest,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

		return bookingService.searchTrips(src, dest, date);
	}

	@GetMapping("/schedules/{id}")
	public ResponseEntity<TripResponseDto> getScheduleById(@PathVariable Long id) {
		return new ResponseEntity<>(bookingService.getTripById(id), HttpStatus.OK);
	}

	@GetMapping("/schedules/{id}/seats")
	public ResponseEntity<List<String>> getBookedSeats(@PathVariable Long id) {
		return ResponseEntity.ok(bookingService.getBookedSeats(id));
	}
	
	@GetMapping("/routes/{routeId}/trips")
	public ResponseEntity<List<TripResponseDto>> getTripsByRoute(@PathVariable Long routeId) {
	    return ResponseEntity.ok(bookingService.getTripsByRoute(routeId));
	}
	
	
	@GetMapping("/schedules/filter")
	public ResponseEntity<List<TripResponseDto>> searchWithFilter(
	        @RequestParam String src,
	        @RequestParam String dest,
	        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
	        @RequestParam BigDecimal min,
	        @RequestParam BigDecimal max) {

	    return ResponseEntity.ok(
	            bookingService.searchTripsWithPrice(src, dest, date, min, max)
	    );
	}


	
	// BOOKING APIs
	

	@PostMapping("/addbusbooking")
	public ResponseEntity<BookingResponseDto> bookBus(@RequestBody BookingDto dto) {

		BookingResponseDto book = bookingService.bookBus(dto);

		return new ResponseEntity<>(book, HttpStatus.CREATED);
	}

	@GetMapping("/bookings")
	public ResponseEntity<List<BookingResponseDto>> getAllBookingsForCustomer() {
		return ResponseEntity.ok(bookingService.getMyBookings());
	}

	@PutMapping("/bookings/{id}/cancel")
	public ResponseEntity<BookingResponseDto> cancelBooking(@PathVariable Long id) {
		return ResponseEntity.ok(bookingService.cancelBooking(id));
	}
	
	@GetMapping("/bookings/status")
	public ResponseEntity<List<BookingResponseDto>> getMyBookingsByStatus(
	        @RequestParam String status) {

	    return ResponseEntity.ok(bookingService.getMyBookingsByStatus(status));
	}



	// REVIEW APIs
	

	@PostMapping("/reviews")
	public ResponseEntity<ReviewResponseDto> addReview(@Valid @RequestBody ReviewRequestDto dto) {
		return new ResponseEntity<>(reviewService.addReview(dto), HttpStatus.CREATED);
	}

	@GetMapping("/reviews/trip/{tripId}")
	public ResponseEntity<List<ReviewResponseDto>> getReviewsByTrip(@PathVariable Long tripId) {
		return ResponseEntity.ok(reviewService.getReviewsByTrip(tripId));
	}

	@GetMapping("/reviews/my")
	public ResponseEntity<List<ReviewResponseDto>> getMyReviews() {
		return ResponseEntity.ok(reviewService.getMyReviews());
	}
}
