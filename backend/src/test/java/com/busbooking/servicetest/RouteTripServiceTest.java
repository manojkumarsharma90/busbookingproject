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

import com.busbooking.dto.*;
import com.busbooking.entity.*;
import com.busbooking.exception.NotAvailableException;
import com.busbooking.repository.*;
import com.busbooking.service.RouteTripService;

@SpringBootTest
public class RouteTripServiceTest {

    @MockitoBean private RouteRepo routeRepo;
    @MockitoBean private TripRepo tripRepo;
    @MockitoBean private BookingMapper mapper;

    @Autowired
    private RouteTripService service;

    // ================= ROUTES =================

    @Test
    public void testGetAllRoutes_Success() {
        when(routeRepo.findAll()).thenReturn(List.of(new Route()));
        when(mapper.toRouteDto(any())).thenReturn(new RouteResponseDto());

        List<RouteResponseDto> result = service.getAllRoutes();

        assertEquals(1, result.size());
    }

    @Test
    public void testGetAllRoutes_Empty() {
        when(routeRepo.findAll()).thenReturn(List.of());

        List<RouteResponseDto> result = service.getAllRoutes();

        assertEquals(0, result.size());
    }

    // ================= ROUTE BY ID =================

    @Test
    public void testGetRouteById_Success() {
        Route route = new Route();

        when(routeRepo.findById(1L)).thenReturn(Optional.of(route));
        when(mapper.toRouteDto(route)).thenReturn(new RouteResponseDto());

        RouteResponseDto result = service.getRouteById(1L);

        assertNotNull(result);
    }

    @Test
    public void testGetRouteById_NotFound() {
        when(routeRepo.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotAvailableException.class, () -> {
            service.getRouteById(1L);
        });
    }

    // ================= TRIPS =================

    @Test
    public void testGetTripsByFare_Success() {
        when(tripRepo.findByFareLessThan(any()))
                .thenReturn(List.of(new Trip()));

        when(mapper.mapTrip(any()))
                .thenReturn(new TripResponseDto());

        List<TripResponseDto> result =
                service.getTripsByFare(BigDecimal.valueOf(1000));

        assertEquals(1, result.size());
    }

    @Test
    public void testGetTripsByFare_Empty() {
        when(tripRepo.findByFareLessThan(any()))
                .thenReturn(List.of());

        List<TripResponseDto> result =
                service.getTripsByFare(BigDecimal.valueOf(1000));

        assertEquals(0, result.size());
    }

    @Test
    public void testGetTripsByDate_Success() {
        when(tripRepo.findByTripDate(any()))
                .thenReturn(List.of(new Trip()));

        when(mapper.mapTrip(any()))
                .thenReturn(new TripResponseDto());

        List<TripResponseDto> result =
                service.getTripsByDate(LocalDate.now());

        assertEquals(1, result.size());
    }

    @Test
    public void testGetTripsByDate_Empty() {
        when(tripRepo.findByTripDate(any()))
                .thenReturn(List.of());

        List<TripResponseDto> result =
                service.getTripsByDate(LocalDate.now());

        assertEquals(0, result.size());
    }
}