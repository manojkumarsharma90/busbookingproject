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


    @Test
    public void testGetRoutesByFromCity_Success() {
        when(routeRepo.findByFromCityIgnoreCase(any()))
                .thenReturn(List.of(new Route()));

        when(mapper.toRouteDto(any()))
                .thenReturn(new RouteResponseDto());

        List<RouteResponseDto> result = service.getRoutesByFromCity("Delhi");

        assertEquals(1, result.size());
    }

    @Test
    public void testGetRoutesByFromCity_Empty() {
        when(routeRepo.findByFromCityIgnoreCase(any()))
                .thenReturn(List.of());

        List<RouteResponseDto> result = service.getRoutesByFromCity("Delhi");

        assertEquals(0, result.size());
    }


    @Test
    public void testGetRoutesByToCity_Success() {
        when(routeRepo.findByToCityIgnoreCase(any()))
                .thenReturn(List.of(new Route()));

        when(mapper.toRouteDto(any()))
                .thenReturn(new RouteResponseDto());

        List<RouteResponseDto> result = service.getRoutesByToCity("Mumbai");

        assertEquals(1, result.size());
    }

    @Test
    public void testGetRoutesByToCity_Empty() {
        when(routeRepo.findByToCityIgnoreCase(any()))
                .thenReturn(List.of());

        List<RouteResponseDto> result = service.getRoutesByToCity("Mumbai");

        assertEquals(0, result.size());
    }



    @Test
    public void testGetRoutesByCities_Success() {
        when(routeRepo.findByFromCityIgnoreCaseAndToCityIgnoreCase(any(), any()))
                .thenReturn(List.of(new Route()));

        when(mapper.toRouteDto(any()))
                .thenReturn(new RouteResponseDto());

        List<RouteResponseDto> result =
                service.getRoutesByCities("Delhi", "Mumbai");

        assertEquals(1, result.size());
    }

    @Test
    public void testGetRoutesByCities_Empty() {
        when(routeRepo.findByFromCityIgnoreCaseAndToCityIgnoreCase(any(), any()))
                .thenReturn(List.of());

        List<RouteResponseDto> result =
                service.getRoutesByCities("Delhi", "Mumbai");

        assertEquals(0, result.size());
    }



    @Test
    public void testGetRoutesByDuration_Success() {
        when(routeRepo.findByDurationGreaterThan(any()))
                .thenReturn(List.of(new Route()));

        when(mapper.toRouteDto(any()))
                .thenReturn(new RouteResponseDto());

        List<RouteResponseDto> result = service.getRoutesByDuration(5);

        assertEquals(1, result.size());
    }

    @Test
    public void testGetRoutesByDuration_Empty() {
        when(routeRepo.findByDurationGreaterThan(any()))
                .thenReturn(List.of());

        List<RouteResponseDto> result = service.getRoutesByDuration(5);

        assertEquals(0, result.size());
    }


    @Test
    public void testGetTripsByDepartureTime_Success() {
        when(tripRepo.findByDepartureTimeAfter(any()))
                .thenReturn(List.of(new Trip()));

        when(mapper.mapTrip(any()))
                .thenReturn(new TripResponseDto());

        List<TripResponseDto> result =
                service.getTripsByDepartureTime(java.time.LocalDateTime.now());

        assertEquals(1, result.size());
    }

    @Test
    public void testGetTripsByDepartureTime_Empty() {
        when(tripRepo.findByDepartureTimeAfter(any()))
                .thenReturn(List.of());

        List<TripResponseDto> result =
                service.getTripsByDepartureTime(java.time.LocalDateTime.now());

        assertEquals(0, result.size());
    }


    @Test
    public void testGetTripsByRouteId_Success() {
        when(tripRepo.findByRoute_RouteId(any()))
                .thenReturn(List.of(new Trip()));

        when(mapper.mapTrip(any()))
                .thenReturn(new TripResponseDto());

        List<TripResponseDto> result = service.getTripsByRouteId(1L);

        assertEquals(1, result.size());
    }

    @Test
    public void testGetTripsByRouteId_Empty() {
        when(tripRepo.findByRoute_RouteId(any()))
                .thenReturn(List.of());

        List<TripResponseDto> result = service.getTripsByRouteId(1L);

        assertEquals(0, result.size());
    }


    @Test
    public void testGetTripsByBusId_Success() {
        when(tripRepo.findByBus_BusId(any()))
                .thenReturn(List.of(new Trip()));

        when(mapper.mapTrip(any()))
                .thenReturn(new TripResponseDto());

        List<TripResponseDto> result = service.getTripsByBusId(1L);

        assertEquals(1, result.size());
    }

    @Test
    public void testGetTripsByBusId_Empty() {
        when(tripRepo.findByBus_BusId(any()))
                .thenReturn(List.of());

        List<TripResponseDto> result = service.getTripsByBusId(1L);

        assertEquals(0, result.size());
    }


    @Test
    public void testGetTripsByAvailableSeats_Success() {
        when(tripRepo.findByAvailableSeatsGreaterThan(any()))
                .thenReturn(List.of(new Trip()));

        when(mapper.mapTrip(any()))
                .thenReturn(new TripResponseDto());

        List<TripResponseDto> result = service.getTripsByAvailableSeats(10);

        assertEquals(1, result.size());
    }

    @Test
    public void testGetTripsByAvailableSeats_Empty() {
        when(tripRepo.findByAvailableSeatsGreaterThan(any()))
                .thenReturn(List.of());

        List<TripResponseDto> result = service.getTripsByAvailableSeats(10);

        assertEquals(0, result.size());
    }
}