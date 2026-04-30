package com.busbooking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbooking.entity.Bus;
import com.busbooking.entity.Driver;
import com.busbooking.entity.Route;
import com.busbooking.entity.Trip;
import com.busbooking.repository.AddressRepo;
import com.busbooking.repository.BusRepo;
import com.busbooking.repository.DriverRepo;
import com.busbooking.repository.RouteRepo;
import com.busbooking.repository.TripRepo;


@Service
public class RouteTripService {

	@Autowired
	private RouteRepo routeRepo;

	@Autowired
	private TripRepo tripRepo;

	@Autowired
	private BusRepo busRepo;

	@Autowired
	private DriverRepo driverRepo;

	@Autowired
	private AddressRepo addressRepo;
	public List<Route> getRoutesByToCity(String toCity) {
	    return routeRepo.findByToCityIgnoreCase(toCity);
	}

	public List<Route> getRoutesByDuration(Integer duration) {
	    return routeRepo.findByDurationGreaterThan(duration);
	}

	public List<Trip> getTripsByFare(BigDecimal fare) {
	    return tripRepo.findByFareLessThan(fare);
	}

	public List<Trip> getTripsByDate(LocalDate date) {
	    return tripRepo.findByTripDate(date);
	}

	public List<Trip> getTripsByDepartureTime(LocalDateTime time) {
	    return tripRepo.findByDepartureTimeAfter(time);
	}

	public List<Route> getRoutesByCities(String fromCity, String toCity) {
	    return routeRepo.findByFromCityIgnoreCaseAndToCityIgnoreCase(fromCity, toCity);
	}

	public List<Route> getRoutesByFromCity(String fromCity) {
	    return routeRepo.findByFromCityIgnoreCase(fromCity);
	}

	public List<Trip> getTripsByRouteId(Long routeId) {
	    return tripRepo.findByRoute_RouteId(routeId);
	}

	public List<Trip> getTripsByBusId(Long busId) {
	    return tripRepo.findByBus_BusId(busId);
	}

	public List<Trip> getTripsByAvailableSeats(Integer seats) {
	    return tripRepo.findByAvailableSeatsGreaterThan(seats);
	}

	public List<Route> getAllRoutes() {
		return routeRepo.findAll();
	}

	public Route getRouteById(Long id) {
		return routeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Route not found"));
	}

	public Route addRoute(Route route) {
		return routeRepo.save(route);
	}

	public Route updateRoute(Long id, Route route) {

		Route existing = routeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Route not found"));

		existing.setFromCity(route.getFromCity());
		existing.setToCity(route.getToCity());
		existing.setBreakPoints(route.getBreakPoints());
		existing.setDuration(route.getDuration());

		return routeRepo.save(existing);
	}

	public void deleteRoute(Long id) {
		routeRepo.deleteById(id);
	}



	public List<Trip> getAllTrips() {
		return tripRepo.findAll();
	}

	public Trip getTripById(Long id) {
		return tripRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Trip not found"));
	}

	public Trip addTrip(Trip trip) {

		if (trip.getRoute() != null && trip.getRoute().getRouteId() != null) {
			Route route = routeRepo.findById(trip.getRoute().getRouteId())
					.orElseThrow(() -> new RuntimeException("Route not found"));
			trip.setRoute(route);
		}

		if (trip.getBus() != null && trip.getBus().getBusId() != null) {
			Bus bus = busRepo.findById(trip.getBus().getBusId())
					.orElseThrow(() -> new RuntimeException("Bus not found"));
			trip.setBus(bus);
		}

		if (trip.getDriver1() != null && trip.getDriver1().getDriverId() != null) {
			Driver d1 = driverRepo.findById(trip.getDriver1().getDriverId())
					.orElseThrow(() -> new RuntimeException("Driver 1 not found"));
			trip.setDriver1(d1);
		}
		if (trip.getDriver2() != null && trip.getDriver2().getDriverId() != null) {
			Driver d2 = driverRepo.findById(trip.getDriver2().getDriverId())
					.orElseThrow(() -> new RuntimeException("Driver 2 not found"));
			trip.setDriver2(d2);
		}

		if (trip.getBoardingAddress() != null && trip.getBoardingAddress().getAddressId() != null) {
			trip.setBoardingAddress(addressRepo.findById(trip.getBoardingAddress().getAddressId())
					.orElseThrow(() -> new RuntimeException("Boarding address not found")));
		}
		if (trip.getDroppingAddress() != null && trip.getDroppingAddress().getAddressId() != null) {
			trip.setDroppingAddress(addressRepo.findById(trip.getDroppingAddress().getAddressId())
					.orElseThrow(() -> new RuntimeException("Dropping address not found")));
		}

		return tripRepo.save(trip);
	}

	public Trip updateTrip(Long id, Trip trip) {

		Trip existing = tripRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Trip not found"));

		existing.setDepartureTime(trip.getDepartureTime());
		existing.setArrivalTime(trip.getArrivalTime());
		existing.setTripDate(trip.getTripDate());
		existing.setAvailableSeats(trip.getAvailableSeats());
		existing.setFare(trip.getFare());

		if (trip.getRoute() != null && trip.getRoute().getRouteId() != null) {
			existing.setRoute(routeRepo.findById(trip.getRoute().getRouteId())
					.orElseThrow(() -> new RuntimeException("Route not found")));
		}
		if (trip.getBus() != null && trip.getBus().getBusId() != null) {
			existing.setBus(busRepo.findById(trip.getBus().getBusId())
					.orElseThrow(() -> new RuntimeException("Bus not found")));
		}

		return tripRepo.save(existing);
	}

	public void deleteTrip(Long id) {
		tripRepo.deleteById(id);
	}
}
