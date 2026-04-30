package com.busbooking.serviceInterface;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.busbooking.dto.RouteRequestDto;
import com.busbooking.dto.RouteResponseDto;
import com.busbooking.dto.TripRequestDto;
import com.busbooking.dto.TripResponseDto;
import com.busbooking.entity.Route;
import com.busbooking.entity.Trip;

public interface IRouteTripService {

    // ROUTE APIs
    List<RouteResponseDto> getAllRoutes();
    RouteResponseDto getRouteById(Long id);
    RouteResponseDto addRoute(RouteRequestDto route);
    RouteResponseDto updateRoute(Long id, RouteRequestDto route);
    void deleteRoute(Long id);

    // TRIP APIs
    List<TripResponseDto> getAllTrips();
    TripResponseDto getTripById(Long id);
    TripResponseDto addTrip(Trip trip);
    TripResponseDto updateTrip(Long id, Trip trip);
    void deleteTrip(Long id);

    // CUSTOM APIs
    List<RouteResponseDto> getRoutesByCities(String fromCity, String toCity);
    List<RouteResponseDto> getRoutesByFromCity(String fromCity);
    List<RouteResponseDto> getRoutesByToCity(String toCity);
    List<RouteResponseDto> getRoutesByDuration(Integer duration);

    List<TripResponseDto> getTripsByRouteId(Long routeId);
    List<TripResponseDto> getTripsByBusId(Long busId);
    List<TripResponseDto> getTripsByAvailableSeats(Integer seats);
    List<TripResponseDto> getTripsByFare(BigDecimal fare);
    List<TripResponseDto> getTripsByDate(LocalDate date);
    List<TripResponseDto> getTripsByDepartureTime(LocalDateTime time);
}