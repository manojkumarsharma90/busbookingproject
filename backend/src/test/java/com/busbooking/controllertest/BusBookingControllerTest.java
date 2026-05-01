package com.busbooking.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.busbooking.controller.BusBookingController;
import com.busbooking.dto.BookingDto;
import com.busbooking.dto.BookingResponseDto;
import com.busbooking.dto.ReviewRequestDto;
import com.busbooking.dto.ReviewResponseDto;
import com.busbooking.dto.TripResponseDto;
import com.busbooking.security.JwtService;
import com.busbooking.service.BookingService;
import com.busbooking.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BusBookingController.class)
@AutoConfigureMockMvc(addFilters = false)
public class BusBookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookingService bookingService;

    @MockitoBean
    private ReviewService reviewService;
    
    @MockitoBean
    private JwtService jwtService;
    
    @MockitoBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;
    
   

  
    // /schedules
 

    @Test
    public void testGetSchedules_Success() throws Exception {

        when(bookingService.searchTrips("Delhi", "Agra", LocalDate.now()))
                .thenReturn(List.of(new TripResponseDto()));

        mockMvc.perform(get("/bus/schedules")
                .param("src", "Delhi")
                .param("dest", "Agra")
                .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetSchedules_Empty() throws Exception {

        when(bookingService.searchTrips("Delhi", "Agra", LocalDate.now()))
                .thenReturn(List.of());

        mockMvc.perform(get("/bus/schedules")
                .param("src", "Delhi")
                .param("dest", "Agra")
                .param("date", LocalDate.now().toString()))
                .andExpect(status().isOk());
    }

   
    //  /schedules/{id}
   

    @Test
    public void testGetScheduleById_Success() throws Exception {

        when(bookingService.getTripById(1L))
                .thenReturn(new TripResponseDto());

        mockMvc.perform(get("/bus/schedules/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetScheduleById_NotFound() throws Exception {

        when(bookingService.getTripById(1L))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/bus/schedules/1"))
                .andExpect(status().isInternalServerError());
    }


    //  /schedules/{id}/seats
   

    @Test
    public void testGetBookedSeats_Success() throws Exception {

        when(bookingService.getBookedSeats(1L))
                .thenReturn(List.of("A1", "B1"));

        mockMvc.perform(get("/bus/schedules/1/seats"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookedSeats_Empty() throws Exception {

        when(bookingService.getBookedSeats(1L))
                .thenReturn(List.of());

        mockMvc.perform(get("/bus/schedules/1/seats"))
                .andExpect(status().isOk());
    }

   
    //  /routes/{routeId}/trips
   

    @Test
    public void testGetTripsByRoute_Success() throws Exception {

        when(bookingService.getTripsByRoute(1L))
                .thenReturn(List.of(new TripResponseDto()));

        mockMvc.perform(get("/bus/routes/1/trips"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTripsByRoute_Empty() throws Exception {

        when(bookingService.getTripsByRoute(1L))
                .thenReturn(List.of());

        mockMvc.perform(get("/bus/routes/1/trips"))
                .andExpect(status().isOk());
    }

   
    //  /schedules/filter
  

    @Test
    public void testSearchWithFilter_Success() throws Exception {

        when(bookingService.searchTripsWithPrice(
                "Delhi", "Agra", LocalDate.now(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(1000)))
                .thenReturn(List.of(new TripResponseDto()));

        mockMvc.perform(get("/bus/schedules/filter")
                .param("src", "Delhi")
                .param("dest", "Agra")
                .param("date", LocalDate.now().toString())
                .param("min", "100")
                .param("max", "1000"))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchWithFilter_Empty() throws Exception {

        when(bookingService.searchTripsWithPrice(
                "Delhi", "Agra", LocalDate.now(),
                BigDecimal.valueOf(100), BigDecimal.valueOf(1000)))
                .thenReturn(List.of());

        mockMvc.perform(get("/bus/schedules/filter")
                .param("src", "Delhi")
                .param("dest", "Agra")
                .param("date", LocalDate.now().toString())
                .param("min", "100")
                .param("max", "1000"))
                .andExpect(status().isOk());
    }

   
    //  POST /addbusbooking
  

    @Test
    public void testBookBus_Success() throws Exception {

        BookingDto dto = new BookingDto();

        when(bookingService.bookBus(dto))
                .thenReturn(new BookingResponseDto());

        mockMvc.perform(post("/bus/addbusbooking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testBookBus_BadRequest() throws Exception {

        when(bookingService.bookBus(any()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(post("/bus/addbusbooking")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isInternalServerError());
    }

  
    //  GET /bookings
  

    @Test
    public void testGetBookings_Success() throws Exception {

        when(bookingService.getMyBookings())
                .thenReturn(List.of(new BookingResponseDto()));

        mockMvc.perform(get("/bus/bookings"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookings_Empty() throws Exception {

        when(bookingService.getMyBookings())
                .thenReturn(List.of());

        mockMvc.perform(get("/bus/bookings"))
                .andExpect(status().isOk());
    }

   
    // /bookings/{id}/cancel
 

    @Test
    public void testCancelBooking_Success() throws Exception {

        when(bookingService.cancelBooking(1L))
                .thenReturn(new BookingResponseDto());

        mockMvc.perform(put("/bus/bookings/1/cancel"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCancelBooking_Error() throws Exception {

        when(bookingService.cancelBooking(1L))
                .thenThrow(new RuntimeException());

        mockMvc.perform(put("/bus/bookings/1/cancel"))
                .andExpect(status().isInternalServerError());
    }

   
    // ✅ GET /bookings/status
   

    @Test
    public void testGetBookingsByStatus_Success() throws Exception {

        when(bookingService.getMyBookingsByStatus("BOOKED"))
                .thenReturn(List.of(new BookingResponseDto()));

        mockMvc.perform(get("/bus/bookings/status")
                .param("status", "BOOKED"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetBookingsByStatus_Invalid() throws Exception {

        when(bookingService.getMyBookingsByStatus("INVALID"))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/bus/bookings/status")
                .param("status", "INVALID"))
                .andExpect(status().isInternalServerError());
    }

   
    //  POST /reviews
   
    @Test
    public void testAddReview_Success() throws Exception {

        ReviewRequestDto dto = new ReviewRequestDto();

        when(reviewService.addReview(dto))
                .thenReturn(new ReviewResponseDto());

        mockMvc.perform(post("/bus/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testAddReview_Error() throws Exception {

        when(reviewService.addReview(any()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(post("/bus/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isInternalServerError());
    }

  
    //  GET /reviews/trip/{tripId}


    @Test
    public void testGetReviewsByTrip_Success() throws Exception {

        when(reviewService.getReviewsByTrip(1L))
                .thenReturn(List.of(new ReviewResponseDto()));

        mockMvc.perform(get("/bus/reviews/trip/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetReviewsByTrip_Empty() throws Exception {

        when(reviewService.getReviewsByTrip(1L))
                .thenReturn(List.of());

        mockMvc.perform(get("/bus/reviews/trip/1"))
                .andExpect(status().isOk());
    }

  
    //  GET /reviews/my
 

    @Test
    public void testGetMyReviews_Success() throws Exception {

        when(reviewService.getMyReviews())
                .thenReturn(List.of(new ReviewResponseDto()));

        mockMvc.perform(get("/bus/reviews/my"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetMyReviews_Empty() throws Exception {

        when(reviewService.getMyReviews())
                .thenReturn(List.of());

        mockMvc.perform(get("/bus/reviews/my"))
                .andExpect(status().isOk());
    }
}