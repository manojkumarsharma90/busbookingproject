package com.busbooking.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.busbooking.dto.*;
import com.busbooking.entity.*;
import com.busbooking.exception.*;
import com.busbooking.repository.*;
import com.busbooking.service.BookingService;

@SpringBootTest
public class BookingServiceTest {

    @MockitoBean private UserRepo userRepo;
    @MockitoBean private TripRepo tripRepo;
    @MockitoBean private BookingRepo bookingRepo;
    @MockitoBean private PassengerRepo passengerRepo;
    @MockitoBean private PaymentRepo paymentRepo;
    @MockitoBean private BookingMapper mapper;

    @Autowired
    private BookingService service;

    
    //  bookBus()
 

    @Test
    public void testBookBus_Success() {

        BookingDto dto = new BookingDto();
        dto.setTripId(1L);

        PassengerDto p = new PassengerDto();
        p.setSeatNo("A1");
        dto.setPassenger(List.of(p));

        User user = new User();
        user.setCustomer(new Customer());

        Trip trip = new Trip();
        trip.setAvailableSeats(5);
        trip.setFare(BigDecimal.valueOf(500));

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));
        when(tripRepo.findById(1L)).thenReturn(Optional.of(trip));
        when(passengerRepo.findBookedSeatNumbers(1L)).thenReturn(List.of());
        when(bookingRepo.save(any())).thenReturn(new Booking());
        when(mapper.toDto(any())).thenReturn(new BookingResponseDto());

        BookingResponseDto result = service.bookBus(dto);

        assertNotNull(result);
    }

    @Test
    public void testBookBus_NoPassenger() {

        BookingDto dto = new BookingDto();
        dto.setTripId(1L);
        dto.setPassenger(List.of());

        assertThrows(BadRequestException.class, () -> {
            service.bookBus(dto);
        });
    }

  
    // ✅ cancelBooking()
    

    @Test
    public void testCancelBooking_Success() {

        Long id = 1L;

        Customer c = new Customer();
        c.setCustomerId(1L);

        User user = new User();
        user.setCustomer(c);

        Trip trip = new Trip();
        trip.setAvailableSeats(5);

        Booking booking = new Booking();
        booking.setCustomer(c);
        booking.setTrip(trip);
        booking.setPassengers(List.of(new Passenger()));
        booking.setDeleted(false);

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));
        when(bookingRepo.findById(id)).thenReturn(Optional.of(booking));
        when(mapper.toDto(any())).thenReturn(new BookingResponseDto());

        BookingResponseDto result = service.cancelBooking(id);

        assertTrue(booking.isDeleted());
        assertNotNull(result);
    }

    @Test
    public void testCancelBooking_AlreadyCancelled() {

        Long id = 1L;

      
        Customer customer = new Customer();
        customer.setCustomerId(1L);

       
        User user = new User();
        user.setCustomer(customer);

       
        Booking booking = new Booking();
        booking.setDeleted(true);
        booking.setCustomer(customer);  

        // mock auth
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        SecurityContextHolder.getContext().setAuthentication(auth);

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));
        when(bookingRepo.findById(id)).thenReturn(Optional.of(booking));

        assertThrows(BadRequestException.class, () -> {
            service.cancelBooking(id);
        });
    }

  
    // ✅ getTripById()
   

    @Test
    public void testGetTripById_Success() {

        Trip trip = new Trip();

        when(tripRepo.findById(1L)).thenReturn(Optional.of(trip));
        when(mapper.mapTrip(trip)).thenReturn(new TripResponseDto());

        TripResponseDto result = service.getTripById(1L);

        assertNotNull(result);
    }

    @Test
    public void testGetTripById_NotFound() {

        when(tripRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getTripById(1L);
        });
    }

  
    // ✅ getBookedSeats()
  

    @Test
    public void testGetBookedSeats_Success() {

        when(tripRepo.findById(1L)).thenReturn(Optional.of(new Trip()));
        when(passengerRepo.findBookedSeatNumbers(1L))
                .thenReturn(List.of("A1", "B1"));

        List<String> result = service.getBookedSeats(1L);

        assertEquals(2, result.size());
    }

    @Test
    public void testGetBookedSeats_TripNotFound() {

        when(tripRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            service.getBookedSeats(1L);
        });
    }

   
    // searchTrips()
   

    @Test
    public void testSearchTrips_Success() {

        when(tripRepo.searchTrips(any(), any(), any()))
                .thenReturn(List.of(new Trip()));

        when(mapper.mapTrip(any())).thenReturn(new TripResponseDto());

        List<TripResponseDto> result =
                service.searchTrips("Delhi", "Agra", LocalDate.now());

        assertEquals(1, result.size());
    }

    @Test
    public void testSearchTrips_Empty() {

        when(tripRepo.searchTrips(any(), any(), any()))
                .thenReturn(List.of());

        List<TripResponseDto> result =
                service.searchTrips("Delhi", "Agra", LocalDate.now());

        assertEquals(0, result.size());
    }
    
    @Test
    public void testGetTripsByRoute_Success() {

        when(tripRepo.findByRoute_RouteId(1L))
                .thenReturn(List.of(new Trip()));

        when(mapper.mapTrip(any()))
                .thenReturn(new TripResponseDto());

        List<TripResponseDto> result = service.getTripsByRoute(1L);

        assertEquals(1, result.size());
    }

    @Test
    public void testGetTripsByRoute_Empty() {

        when(tripRepo.findByRoute_RouteId(1L))
                .thenReturn(List.of());

        List<TripResponseDto> result = service.getTripsByRoute(1L);

        assertEquals(0, result.size());
    }

   
    //  searchTripsWithPrice()
   

    @Test
    public void testSearchTripsWithPrice_Success() {

        when(tripRepo.searchTripsWithPriceFilter(any(), any(), any(), any(), any()))
                .thenReturn(List.of(new Trip()));

        when(mapper.mapTrip(any()))
                .thenReturn(new TripResponseDto());

        List<TripResponseDto> result =
                service.searchTripsWithPrice("Delhi", "Agra",
                        LocalDate.now(), BigDecimal.valueOf(100), BigDecimal.valueOf(1000));

        assertEquals(1, result.size());
    }

    @Test
    public void testSearchTripsWithPrice_Empty() {

        when(tripRepo.searchTripsWithPriceFilter(any(), any(), any(), any(), any()))
                .thenReturn(List.of());

        List<TripResponseDto> result =
                service.searchTripsWithPrice("Delhi", "Agra",
                        LocalDate.now(), BigDecimal.valueOf(100), BigDecimal.valueOf(1000));

        assertEquals(0, result.size());
    }

  
    // ✅ getMyBookings()
   

    @Test
    public void testGetMyBookings_Success() {

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        SecurityContextHolder.getContext().setAuthentication(auth);

        Customer customer = new Customer();
        customer.setCustomerId(1L);

        User user = new User();
        user.setCustomer(customer);

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));

        when(bookingRepo.findByCustomer_CustomerIdAndDeletedFalseOrderByBookingDateDesc(1L))
                .thenReturn(List.of(new Booking()));

        when(mapper.toDto(any()))
                .thenReturn(new BookingResponseDto());

        List<BookingResponseDto> result = service.getMyBookings();

        assertEquals(1, result.size());
    }

    @Test
    public void testGetMyBookings_NoCustomer() {

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = new User(); // no customer

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> {
            service.getMyBookings();
        });
    }

  
    // ✅ getMyBookingsByStatus()


    @Test
    public void testGetMyBookingsByStatus_Success() {

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        SecurityContextHolder.getContext().setAuthentication(auth);

        Customer customer = new Customer();
        customer.setCustomerId(1L);

        User user = new User();
        user.setCustomer(customer);

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));

        when(bookingRepo.findByCustomer_CustomerIdAndStatusAndDeletedFalse(
                1L, BookingStatus.BOOKED))
                .thenReturn(List.of(new Booking()));

        when(mapper.toDto(any()))
                .thenReturn(new BookingResponseDto());

        List<BookingResponseDto> result =
                service.getMyBookingsByStatus("BOOKED");

        assertEquals(1, result.size());
    }

    @Test
    public void testGetMyBookingsByStatus_InvalidStatus() {

       
        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        SecurityContextHolder.getContext().setAuthentication(auth);

      
        Customer customer = new Customer();
        customer.setCustomerId(1L);

        User user = new User();
        user.setCustomer(customer);

        when(userRepo.findByUsername("user"))
                .thenReturn(Optional.of(user));

   
        assertThrows(BadRequestException.class, () -> {
            service.getMyBookingsByStatus("INVALID");
        });
    }
   
    // ✅ getCustomerId()

    @Test
    public void testGetCustomerId_Success() {

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        SecurityContextHolder.getContext().setAuthentication(auth);

        Customer customer = new Customer();
        customer.setCustomerId(10L);

        User user = new User();
        user.setCustomer(customer);

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));

        Long result = service.getCustomerId();

        assertEquals(10L, result);
    }

    @Test
    public void testGetCustomerId_NoCustomer() {

        Authentication auth = mock(Authentication.class);
        when(auth.getName()).thenReturn("user");
        SecurityContextHolder.getContext().setAuthentication(auth);

        User user = new User(); // no customer

        when(userRepo.findByUsername("user")).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class, () -> {
            service.getCustomerId();
        });
    }
}