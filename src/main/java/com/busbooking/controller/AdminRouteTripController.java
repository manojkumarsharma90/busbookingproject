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

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
@RestController
@RequestMapping("/admin")
@CrossOrigin
@SecurityRequirement(name = "BearerAuth")
public class AdminRouteTripController {
	@Autowired
	private RouteTripService routeTripService;
	@GetMapping("/routes")
	public List<Route> getAllRoutes() {
		return routeTripService.getAllRoutes();
	}
	
	@GetMapping("/routes/{id}")
	public Route getRouteById(@PathVariable Long id) {
		return routeTripService.getRouteById(id);
	}
	
	@PostMapping("/routes")
	public Route addRoute(@RequestBody Route route) {
		return routeTripService.addRoute(route);
	}
	
	@PutMapping("/routes/{id}")
	public Route updateRoute(@PathVariable Long id, @RequestBody Route route) {
		return routeTripService.updateRoute(id, route);
	}
	
	@DeleteMapping("/routes/{id}")
	public ResponseEntity<String> deleteRoute(@PathVariable Long id) {
		routeTripService.deleteRoute(id);
		return ResponseEntity.ok("Route deleted successfully");
	}
	
	@GetMapping("/trips")
	public List<Trip> getAllTrips() {
		return routeTripService.getAllTrips();
	}
	@GetMapping("/trips/{id}")
	public Trip getTripById(@PathVariable Long id) {
		return routeTripService.getTripById(id);
	}
	
	@PostMapping("/trips")
	public Trip addTrip(@RequestBody Trip trip) {
		return routeTripService.addTrip(trip);
	}
	
	@PutMapping("/trips/{id}")
	public Trip updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
		return routeTripService.updateTrip(id, trip);
	}

	@DeleteMapping("/trips/{id}")
	public ResponseEntity<String> deleteTrip(@PathVariable Long id) {
		routeTripService.deleteTrip(id);
		return ResponseEntity.ok("Trip deleted successfully");
	}
	
	@GetMapping("/routes/search/{fromCity}/{toCity}")
	public List<Route> getRoutesByCities(
	        @PathVariable String fromCity,
	        @PathVariable String toCity) {

	    return routeTripService.getRoutesByCities(fromCity, toCity);
	}


	
	@GetMapping("/routes/from/{fromCity}")
	public List<Route> getRoutesByFromCity(
	        @PathVariable String fromCity) {

	    return routeTripService.getRoutesByFromCity(fromCity);
	}


	
	@GetMapping("/trips/route/{routeId}")
	public List<Trip> getTripsByRouteId(
	        @PathVariable Long routeId) {

	    return routeTripService.getTripsByRouteId(routeId);
	}


	
	@GetMapping("/trips/bus/{busId}")
	public List<Trip> getTripsByBusId(
	        @PathVariable Long busId) {

	    return routeTripService.getTripsByBusId(busId);
	}


	
	@GetMapping("/trips/seats/{seats}")
	public List<Trip> getTripsByAvailableSeats(
	        @PathVariable Integer seats) {

	    return routeTripService.getTripsByAvailableSeats(seats);
	}
}
