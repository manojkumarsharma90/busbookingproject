package com.busbooking.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import com.busbooking.entity.Booking;
import com.busbooking.entity.Passenger;
import com.busbooking.entity.Review;
import com.busbooking.entity.Route;
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

	public TripResponseDto mapTrip(Trip trip) {

	    TripResponseDto dto = new TripResponseDto();

	    dto.setTripId(trip.getTripId());

	   
	    if (trip.getRoute() != null) {
	        dto.setRouteId(trip.getRoute().getRouteId());
	        dto.setSource(trip.getRoute().getFromCity());
	        dto.setDestination(trip.getRoute().getToCity());
	    }

	
	    if (trip.getBus() != null) {
	        dto.setBusId(trip.getBus().getBusId());
	        dto.setBusNumber(trip.getBus().getRegistrationNumber());
	        dto.setBusType(trip.getBus().getType());
	    }

	
	    dto.setDepartureTime(trip.getDepartureTime());
	    dto.setTripDate(trip.getTripDate());

	 
	    dto.setAvailableSeats(trip.getAvailableSeats());
	    dto.setFare(trip.getFare());

	    return dto;
	}
	
	
	public PassengerDto mapPassenger(Passenger p) {
		return new PassengerDto(p.getName(), p.getAge(), p.getGender(), p.getSeatNo());
	}
	
	
	
	public ReviewResponseDto mapToReviewResponseDto(Review review) {

	    ReviewResponseDto dto = new ReviewResponseDto();

	    if (review == null) {
	        return dto;
	    }

	    // Trip
	    if (review.getTrip() != null) {
	        dto.setTripId(review.getTrip().getTripId());
	    }

	    dto.setRating(review.getRating());
	    dto.setComment(review.getComment());

	    return dto;
	}
	
	
	
	 public  RouteResponseDto toRouteDto(Route route) {
	        if (route == null) {
	            return null;
	        }

	        return new RouteResponseDto(
	                route.getRouteId(),
	                route.getFromCity(),
	                route.getToCity(),
	                route.getBreakPoints(),
	                route.getDuration()
	        );
	    }
}