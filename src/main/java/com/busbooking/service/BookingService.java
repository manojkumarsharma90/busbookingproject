package com.busbooking.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.busbooking.dto.BookingDto;
import com.busbooking.dto.PassengerDto;
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

@Service
public class BookingService {

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

    public List<Trip> searchTrips(String fromCity, String toCity, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        return tripRepo.searchTrips(fromCity, toCity, startOfDay, endOfDay);
    }

    public Trip getTripById(Long tripId) {
        return tripRepo.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + tripId));
    }

    public List<String> getBookedSeats(Long tripId) {

        tripRepo.findById(tripId)
                .orElseThrow(() -> new ResourceNotFoundException("Trip not found with id " + tripId));

        List<Booking> bookedSeats =
                bookingRepo.findByTrip_TripIdAndStatus(tripId, BookingStatus.BOOKED);

        List<String> seats = new ArrayList<>();

        for (Booking b : bookedSeats) {
            if (!b.isDeleted()) {
                seats.add(String.valueOf(b.getSeatNumber()));
            }
        }

        return seats;
    }

    public Booking bookBus(BookingDto dto) {

        if (dto.getPassenger() == null || dto.getPassenger().isEmpty()) {
            throw new BadRequestException("At least one passenger is required for booking");
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getCustomer() == null) {
            throw new BadRequestException(
                    "User does not have a customer profile. Please complete registration."
            );
        }

        Trip trip = tripRepo.findById(dto.getTripId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Trip not found with id " + dto.getTripId()
                ));

        if (dto.getPassenger().size() > trip.getAvailableSeats()) {
            throw new SeatNotAvailableException("Enough seats are not available");
        }

        List<Booking> existingBookings =
                bookingRepo.findByTrip_TripIdAndStatus(dto.getTripId(), BookingStatus.BOOKED);

        Set<Integer> bookedSeatNumbers = existingBookings.stream()
                .filter(b -> !b.isDeleted())
                .map(Booking::getSeatNumber)
                .collect(Collectors.toSet());

        Booking savedBooking = null;

        for (PassengerDto p : dto.getPassenger()) {

            int seatNum = Integer.parseInt(p.getSeatNo());

            if (bookedSeatNumbers.contains(seatNum)) {
                throw new SeatNotAvailableException("Seat " + seatNum + " is already booked");
            }

            if (seatNum < 1 || seatNum > trip.getBus().getCapacity()) {
                throw new BadRequestException(
                        "Seat " + seatNum + " is invalid. Bus has "
                                + trip.getBus().getCapacity() + " seats."
                );
            }

            bookedSeatNumbers.add(seatNum);

            Booking booking = new Booking();
            booking.setTrip(trip);
            booking.setCustomer(user.getCustomer());
            booking.setSeatNumber(seatNum);
            booking.setStatus(BookingStatus.BOOKED);
            booking.setBookingDate(LocalDateTime.now());

            savedBooking = bookingRepo.save(booking);

            Passenger passenger = new Passenger();
            passenger.setName(p.getName());
            passenger.setAge(p.getAge());
            passenger.setGender(p.getGender());
            passenger.setSeatNo(p.getSeatNo());
            passenger.setBooking(savedBooking);

            passengerRepo.save(passenger);

            Payment payment = new Payment();
            payment.setBooking(savedBooking);
            payment.setCustomer(user.getCustomer());
            payment.setAmount(trip.getFare());
            payment.setPaymentDate(LocalDateTime.now());
            payment.setPaymentStatus(PaymentStatus.SUCCESS);

            paymentRepo.save(payment);
        }

        trip.setAvailableSeats(trip.getAvailableSeats() - dto.getPassenger().size());
        tripRepo.save(trip);

        return savedBooking;
    }

    public List<Booking> getMyBookings() {
        Long custId = getCustomerId();

        return bookingRepo
                .findByCustomer_CustomerIdAndDeletedFalseOrderByBookingDateDesc(custId);
    }

    public Booking cancelBooking(Long bookingId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Booking booking = bookingRepo.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Booking not found with id " + bookingId
                ));

        if (user.getCustomer() == null ||
                !booking.getCustomer().getCustomerId()
                        .equals(user.getCustomer().getCustomerId())) {

            throw new BadRequestException("You can only cancel your own bookings");
        }

        if (booking.isDeleted()) {
            throw new BadRequestException("Booking is already cancelled");
        }

        booking.setDeleted(true);
        booking.setStatus(BookingStatus.AVAILABLE);

        bookingRepo.save(booking);

        Trip trip = booking.getTrip();
        trip.setAvailableSeats(trip.getAvailableSeats() + 1);

        tripRepo.save(trip);

        return booking;
    }

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