package com.busbooking.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.busbooking.entity.Booking;
import com.busbooking.entity.Passenger;
import com.busbooking.entity.Trip;

@Component
public class BookingMapper {

	public BookingResponseDto toDto(Booking booking) {

		BookingResponseDto dto = new BookingResponseDto();

		dto.setBookingId(booking.getBookingId());
		dto.setStatus(booking.getStatus().name());
		dto.setBookingDate(booking.getBookingDate());
		dto.setCustomerName(booking.getCustomer().getName());

		// Trip
		dto.setTrip(mapTrip(booking.getTrip()));

		// Passengers
		List<PassengerDto> passengers = booking.getPassengers().stream().map(this::mapPassenger).toList();

		dto.setPassengers(passengers);
		dto.setTotalSeats(passengers.size());

		return dto;
	}

	// 🔹 Separate mapping methods

	public TripInfoDto mapTrip(Trip trip) {
		TripInfoDto dto = new TripInfoDto();

		dto.setTripId(trip.getTripId());
		dto.setFromCity(trip.getRoute().getFromCity());
		dto.setToCity(trip.getRoute().getToCity());
		dto.setDepartureTime(trip.getDepartureTime());
		dto.setFare(trip.getFare());

		return dto;
	}

	public PassengerDto mapPassenger(Passenger p) {
		return new PassengerDto(p.getName(), p.getAge(), p.getGender(), p.getSeatNo());
	}
}