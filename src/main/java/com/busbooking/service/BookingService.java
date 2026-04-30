package com.busbooking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.busbooking.dto.BookingDto;
import com.busbooking.dto.BookingMapper;
import com.busbooking.dto.BookingResponseDto;
import com.busbooking.dto.PassengerDto;
import com.busbooking.dto.TripResponseDto;
import com.busbooking.entity.Booking;
import com.busbooking.entity.BookingStatus;
import com.busbooking.entity.Passenger;
import com.busbooking.entity.Payment;
import com.busbooking.entity.PaymentStatus;
import com.busbooking.entity.Trip;
import com.busbooking.entity.User;
import com.busbooking.exception.BadRequestException;
import com.busbooking.exception.ResourceNotFoundException;
import com.busbooking.exception.SeatNotAvailableException;
import com.busbooking.repository.BookingRepo;
import com.busbooking.repository.PassengerRepo;
import com.busbooking.repository.PaymentRepo;
import com.busbooking.repository.TripRepo;
import com.busbooking.repository.UserRepo;
import com.busbooking.serviceInterface.IBookingService;

import jakarta.transaction.Transactional;

@Service
public class BookingService implements IBookingService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private TripRepo tripRepo;

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private PassengerRepo passengerRepo;

	@Autowired
	private PaymentRepo paymentRepo;

	@Autowired
	private BookingMapper mapper;

	// Trip Search + Seat Availability

	public List<TripResponseDto> searchTrips(String fromCity, String toCity, LocalDate date) {
		List<Trip> trip = tripRepo.searchTrips(fromCity, toCity, date);

		return trip.stream().map(mapper::mapTrip).toList();

	}

	public TripResponseDto getTripById(Long tripId) {
		Trip trip = tripRepo.findById(tripId)
				.orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + tripId));

		return mapper.mapTrip(trip);
	}

	public List<String> getBookedSeats(Long tripId) {

		tripRepo.findById(tripId).orElseThrow(() -> new ResourceNotFoundException("Trip not found"));

		return passengerRepo.findBookedSeatNumbers(tripId);
	}

	public List<TripResponseDto> getTripsByRoute(Long routeId) {
		return tripRepo.findByRoute_RouteId(routeId).stream().map(mapper::mapTrip).toList();
	}

	public List<TripResponseDto> searchTripsWithPrice(String fromCity, String toCity, LocalDate date, BigDecimal min,
			BigDecimal max) {

		return tripRepo.searchTripsWithPriceFilter(fromCity, toCity, date, min, max).stream().map(mapper::mapTrip)
				.toList();
	}

	// Booking

	@Transactional
	public BookingResponseDto bookBus(BookingDto dto) {

		if (dto.getPassenger() == null || dto.getPassenger().isEmpty()) {
			throw new BadRequestException("At least one passenger is required");
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Trip trip = tripRepo.findById(dto.getTripId())
				.orElseThrow(() -> new ResourceNotFoundException("Trip not found"));

		if (dto.getPassenger().size() > trip.getAvailableSeats()) {
			throw new SeatNotAvailableException("Not enough seats available");
		}

		List<Booking> existingBookings = bookingRepo.findByTrip_TripIdAndStatus(dto.getTripId(), BookingStatus.BOOKED);

		Set<String> bookedSeats = passengerRepo.findBookedSeatNumbers(dto.getTripId()).stream()
				.map(s -> s.toUpperCase().trim()).collect(Collectors.toSet()); // updated by me

		Booking booking = new Booking();
		booking.setTrip(trip);
		booking.setCustomer(user.getCustomer());
		booking.setStatus(BookingStatus.BOOKED);
		booking.setBookingDate(LocalDateTime.now());

		List<Passenger> passengers = new ArrayList<>();
		Set<String> requestedSeats = new HashSet<>();

		for (PassengerDto p : dto.getPassenger()) {

			String seat = p.getSeatNo().toUpperCase().trim();

			if (!requestedSeats.add(seat)) {
				throw new BadRequestException("Duplicate seat in request: " + seat);
			}

			if (bookedSeats.contains(seat)) {
				throw new SeatNotAvailableException("Seat already booked: " + seat);
			}

			Passenger passenger = new Passenger();
			passenger.setName(p.getName());
			passenger.setAge(p.getAge());
			passenger.setGender(p.getGender());
			passenger.setSeatNo(seat);
			passenger.setBooking(booking);

			passengers.add(passenger);
		}

		booking.setPassengers(passengers);

		Booking savedBooking = bookingRepo.save(booking);

		Payment payment = new Payment();
		payment.setBooking(savedBooking);
		payment.setCustomer(user.getCustomer());
		payment.setAmount(trip.getFare().multiply(BigDecimal.valueOf(passengers.size())));
		payment.setPaymentDate(LocalDateTime.now());
		payment.setPaymentStatus(PaymentStatus.SUCCESS);

		paymentRepo.save(payment);

		trip.setAvailableSeats(trip.getAvailableSeats() - passengers.size());
		tripRepo.save(trip);

		return mapper.toDto(savedBooking); // changes done by me
	}

	public List<BookingResponseDto> getMyBookings() {
		Long custId = getCustomerId();

		List<Booking> bookings = bookingRepo.findByCustomer_CustomerIdAndDeletedFalseOrderByBookingDateDesc(custId);
		return bookings.stream().map(mapper::toDto).toList();
	}

	public BookingResponseDto cancelBooking(Long bookingId) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		Booking booking = bookingRepo.findById(bookingId)
				.orElseThrow(() -> new ResourceNotFoundException("Booking not found with id " + bookingId));

		if (user.getCustomer() == null
				|| !booking.getCustomer().getCustomerId().equals(user.getCustomer().getCustomerId())) {
			throw new BadRequestException("You can only cancel your own bookings");
		}

		if (booking.isDeleted()) {
			throw new BadRequestException("Booking is already cancelled");
		}

		booking.setDeleted(true);
		booking.setStatus(BookingStatus.CANCELLED);
		bookingRepo.save(booking);

		// restore the seat
		Trip trip = booking.getTrip();
		int passengerCount = booking.getPassengers().size();
		trip.setAvailableSeats(trip.getAvailableSeats() + passengerCount);
		tripRepo.save(trip);

		return mapper.toDto(booking);
	}

	public List<BookingResponseDto> getMyBookingsByStatus(String status) {

		Long customerId = getCustomerId();

		BookingStatus bookingStatus;

		try {
			bookingStatus = BookingStatus.valueOf(status.toUpperCase());
		} catch (Exception e) {
			throw new BadRequestException("Invalid booking status");
		}

		return bookingRepo.findByCustomer_CustomerIdAndStatusAndDeletedFalse(customerId, bookingStatus).stream()
				.map(mapper::toDto).toList();
	}

	// Helpers

	public Long getCustomerId() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();

		User user = userRepo.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User not found"));

		if (user.getCustomer() == null) {
			throw new BadRequestException("User does not have a customer profile");
		}

		return user.getCustomer().getCustomerId();
	}
}