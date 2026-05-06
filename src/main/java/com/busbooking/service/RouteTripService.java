package com.busbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbooking.entity.Bus;
import com.busbooking.entity.Driver;
import com.busbooking.entity.Route;
import com.busbooking.entity.Trip;
import com.busbooking.repo.AddressRepo;
import com.busbooking.repo.BusRepo;
import com.busbooking.repo.DriverRepo;
import com.busbooking.repo.RouteRepo;
import com.busbooking.repo.TripRepo;

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


	// ============================
	// ROUTE
	// ============================

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


	// ============================
	// TRIP
	// ============================

	public List<Trip> getAllTrips() {
		return tripRepo.findAll();
	}

	public Trip getTripById(Long id) {
		return tripRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Trip not found"));
	}

	public Trip addTrip(Trip trip) {

		// resolve route
		if (trip.getRoute() != null && trip.getRoute().getRouteId() != null) {
			Route route = routeRepo.findById(trip.getRoute().getRouteId())
					.orElseThrow(() -> new RuntimeException("Route not found"));
			trip.setRoute(route);
		}

		// resolve bus
		if (trip.getBus() != null && trip.getBus().getBusId() != null) {
			Bus bus = busRepo.findById(trip.getBus().getBusId())
					.orElseThrow(() -> new RuntimeException("Bus not found"));
			trip.setBus(bus);
		}

		// resolve drivers
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

		// resolve addresses
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
