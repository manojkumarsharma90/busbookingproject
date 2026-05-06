package com.busbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.entity.Route;
import com.busbooking.entity.Trip;
import com.busbooking.service.RouteTripService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminRouteTripController {

	@Autowired
	private RouteTripService routeTripService;


	// ============================
	// ROUTE APIs
	// ============================

	// GET all routes
	@GetMapping("/routes")
	public List<Route> getAllRoutes() {
		return routeTripService.getAllRoutes();
	}

	// GET route by id
	@GetMapping("/routes/{id}")
	public Route getRouteById(@PathVariable Long id) {
		return routeTripService.getRouteById(id);
	}

	// ADD route
	@PostMapping("/routes")
	public Route addRoute(@RequestBody Route route) {
		return routeTripService.addRoute(route);
	}

	// UPDATE route
	@PutMapping("/routes/{id}")
	public Route updateRoute(@PathVariable Long id, @RequestBody Route route) {
		return routeTripService.updateRoute(id, route);
	}

	// DELETE route
	@DeleteMapping("/routes/{id}")
	public ResponseEntity<String> deleteRoute(@PathVariable Long id) {
		routeTripService.deleteRoute(id);
		return ResponseEntity.ok("Route deleted successfully");
	}


	// ============================
	// TRIP APIs
	// ============================

	// GET all trips
	@GetMapping("/trips")
	public List<Trip> getAllTrips() {
		return routeTripService.getAllTrips();
	}

	// GET trip by id
	@GetMapping("/trips/{id}")
	public Trip getTripById(@PathVariable Long id) {
		return routeTripService.getTripById(id);
	}

	// ADD trip
	@PostMapping("/trips")
	public Trip addTrip(@RequestBody Trip trip) {
		return routeTripService.addTrip(trip);
	}

	// UPDATE trip
	@PutMapping("/trips/{id}")
	public Trip updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
		return routeTripService.updateTrip(id, trip);
	}

	// DELETE trip
	@DeleteMapping("/trips/{id}")
	public ResponseEntity<String> deleteTrip(@PathVariable Long id) {
		routeTripService.deleteTrip(id);
		return ResponseEntity.ok("Trip deleted successfully");
	}
}
