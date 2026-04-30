package com.busbooking.serviceInterface;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.busbooking.dto.BookingDto;
import com.busbooking.dto.BookingResponseDto;
import com.busbooking.dto.TripResponseDto;

public interface IBookingService{

   
    // Trip Search + Seat Availability
  

    List<TripResponseDto> searchTrips(String fromCity, String toCity, LocalDate date);

    TripResponseDto getTripById(Long tripId);

    List<String> getBookedSeats(Long tripId);

    List<TripResponseDto> getTripsByRoute(Long routeId);

    List<TripResponseDto> searchTripsWithPrice(
            String fromCity,
            String toCity,
            LocalDate date,
            BigDecimal min,
            BigDecimal max
    );

   
    // Booking
  

    BookingResponseDto bookBus(BookingDto dto);

    List<BookingResponseDto> getMyBookings();

    BookingResponseDto cancelBooking(Long bookingId);

    List<BookingResponseDto> getMyBookingsByStatus(String status);

   
    // Helper
   

    Long getCustomerId();
}