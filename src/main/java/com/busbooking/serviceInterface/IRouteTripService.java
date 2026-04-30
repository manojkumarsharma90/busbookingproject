package com.busbooking.serviceInterface;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.busbooking.entity.Route;
import com.busbooking.entity.Trip;

public interface IRouteTripService {

    // ROUTE APIs
    List<Route> getAllRoutes();
    Route getRouteById(Long id);
    Route addRoute(Route route);
    Route updateRoute(Long id, Route route);
    void deleteRoute(Long id);

    // TRIP APIs
    List<Trip> getAllTrips();
    Trip getTripById(Long id);
    Trip addTrip(Trip trip);
    Trip updateTrip(Long id, Trip trip);
    void deleteTrip(Long id);

    // CUSTOM APIs
    List<Route> getRoutesByCities(String fromCity, String toCity);
    List<Route> getRoutesByFromCity(String fromCity);
    List<Route> getRoutesByToCity(String toCity);
    List<Route> getRoutesByDuration(Integer duration);

    List<Trip> getTripsByRouteId(Long routeId);
    List<Trip> getTripsByBusId(Long busId);
    List<Trip> getTripsByAvailableSeats(Integer seats);
    List<Trip> getTripsByFare(BigDecimal fare);
    List<Trip> getTripsByDate(LocalDate date);
    List<Trip> getTripsByDepartureTime(LocalDateTime time);
}