package com.busbooking.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.busbooking.dto.BookingMapper;
import com.busbooking.dto.ReviewRequestDto;
import com.busbooking.dto.ReviewResponseDto;
import com.busbooking.entity.Customer;
import com.busbooking.entity.Review;
import com.busbooking.entity.Trip;
import com.busbooking.entity.User;
import com.busbooking.exception.BadRequestException;
import com.busbooking.exception.ResourceNotFoundException;
import com.busbooking.repository.ReviewRepo;
import com.busbooking.repository.TripRepo;
import com.busbooking.repository.UserRepo;
import com.busbooking.service.ReviewService;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepo reviewRepo;

    @Mock
    private TripRepo tripRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private BookingMapper mapper;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private ReviewService reviewService;

    private User user;
    private Customer customer;
    private Trip trip;
    private Review review;
    private ReviewRequestDto requestDto;
    private ReviewResponseDto responseDto;

    @BeforeEach
    void setUp() {
        
        // Setup customer
        customer = new Customer();
        customer.setCustomerId(1L);
        customer.setName("Test Customer");

        // Setup user
        user = new User();
        user.setUsername("testuser");
        user.setCustomer(customer);

        // Setup trip
        trip = new Trip();
        trip.setTripId(1L);

        // Setup review
        review = new Review();
        review.setCustomer(customer);
        review.setTrip(trip);
        review.setRating(5);
        review.setComment("Great trip!");
        review.setReviewDate(LocalDateTime.now());

        // Setup DTOs
        requestDto = new ReviewRequestDto();
        requestDto.setTripId(1L);
        requestDto.setRating(5);
        requestDto.setComment("Great trip!");

        responseDto = new ReviewResponseDto();
        responseDto.setRating(5);
        responseDto.setComment("Great trip!");
    }
    
    
    private void mockSecurityContext() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("testuser");
    }

   
    // addReview() tests
    

    @Test
    void addReview_Success() {
    	 mockSecurityContext();
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(tripRepo.findById(1L)).thenReturn(Optional.of(trip));
        when(reviewRepo.save(any(Review.class))).thenReturn(review);
        when(mapper.mapToReviewResponseDto(review)).thenReturn(responseDto);

        ReviewResponseDto result = reviewService.addReview(requestDto);

        assertNotNull(result);
        assertEquals(5, result.getRating());
        assertEquals("Great trip!", result.getComment());

        verify(reviewRepo, times(1)).save(any(Review.class));
        verify(mapper, times(1)).mapToReviewResponseDto(review);
    }

    @Test
    void addReview_UserNotFound_ThrowsException() {
    	 mockSecurityContext();
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.addReview(requestDto));

        verify(reviewRepo, never()).save(any());
    }

    @Test
    void addReview_UserHasNoCustomerProfile_ThrowsException() {
    	 mockSecurityContext();
        user.setCustomer(null); // no customer profile
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class,
                () -> reviewService.addReview(requestDto));

        verify(reviewRepo, never()).save(any());
    }

    @Test
    void addReview_TripNotFound_ThrowsException() {
    	 mockSecurityContext();
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(tripRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.addReview(requestDto));

        verify(reviewRepo, never()).save(any());
    }

  
    // getReviewsByTrip() tests
    

    @Test
    void getReviewsByTrip_Success_ReturnsList() {
        Review review2 = new Review();
        review2.setRating(4);
        review2.setComment("Good trip!");

        ReviewResponseDto responseDto2 = new ReviewResponseDto();
        responseDto2.setRating(4);
        responseDto2.setComment("Good trip!");

        when(reviewRepo.findByTrip_TripId(1L)).thenReturn(List.of(review, review2));
        when(mapper.mapToReviewResponseDto(review)).thenReturn(responseDto);
        when(mapper.mapToReviewResponseDto(review2)).thenReturn(responseDto2);

        List<ReviewResponseDto> result = reviewService.getReviewsByTrip(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(5, result.get(0).getRating());
        assertEquals(4, result.get(1).getRating());

        verify(reviewRepo, times(1)).findByTrip_TripId(1L);
    }

    @Test
    void getReviewsByTrip_NoReviews_ReturnsEmptyList() {
        when(reviewRepo.findByTrip_TripId(99L)).thenReturn(List.of());

        List<ReviewResponseDto> result = reviewService.getReviewsByTrip(99L);

        assertNotNull(result);
        assertTrue(result.isEmpty());

        verify(reviewRepo, times(1)).findByTrip_TripId(99L);
    }

    @Test
    void getReviewsByTrip_SingleReview_ReturnsSingleItem() {
        when(reviewRepo.findByTrip_TripId(1L)).thenReturn(List.of(review));
        when(mapper.mapToReviewResponseDto(review)).thenReturn(responseDto);

        List<ReviewResponseDto> result = reviewService.getReviewsByTrip(1L);

        assertEquals(1, result.size());
        assertEquals("Great trip!", result.get(0).getComment());
    }

  
    // getMyReviews() tests
   

    @Test
    void getMyReviews_Success_ReturnsList() {
    	 mockSecurityContext();
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(reviewRepo.findByCustomer_CustomerId(1L)).thenReturn(List.of(review));
        when(mapper.mapToReviewResponseDto(review)).thenReturn(responseDto);

        List<ReviewResponseDto> result = reviewService.getMyReviews();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Great trip!", result.get(0).getComment());

        verify(reviewRepo, times(1)).findByCustomer_CustomerId(1L);
    }

    @Test
    void getMyReviews_NoReviews_ReturnsEmptyList() {
    	 mockSecurityContext();
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(user));
        when(reviewRepo.findByCustomer_CustomerId(1L)).thenReturn(List.of());

        List<ReviewResponseDto> result = reviewService.getMyReviews();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getMyReviews_UserNotFound_ThrowsException() {
    	 mockSecurityContext();
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> reviewService.getMyReviews());

        verify(reviewRepo, never()).findByCustomer_CustomerId(any());
    }

    @Test
    void getMyReviews_UserHasNoCustomerProfile_ThrowsException() {
    	 mockSecurityContext();
        user.setCustomer(null);
        when(userRepo.findByUsername("testuser")).thenReturn(Optional.of(user));

        assertThrows(BadRequestException.class,
                () -> reviewService.getMyReviews());

        verify(reviewRepo, never()).findByCustomer_CustomerId(any());
    }
}