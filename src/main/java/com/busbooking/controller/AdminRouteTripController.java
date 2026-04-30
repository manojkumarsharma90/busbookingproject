package com.busbooking.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

import com.busbooking.dto.RouteRequestDto;
import com.busbooking.dto.RouteResponseDto;
import com.busbooking.dto.TripRequestDto;
import com.busbooking.dto.TripResponseDto;
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
	public List<RouteResponseDto> getAllRoutes() {
		return routeTripService.getAllRoutes();
	}
	
	@GetMapping("/routes/{id}")
	public RouteResponseDto getRouteById(@PathVariable Long id) {
		return routeTripService.getRouteById(id);
	}
	
	@PostMapping("/routes")
	public RouteResponseDto addRoute(@RequestBody RouteRequestDto route) {
		return routeTripService.addRoute(route);
	}
	
	@PutMapping("/routes/{id}")
	public RouteResponseDto updateRoute(@PathVariable Long id, @RequestBody RouteRequestDto route) {
		return routeTripService.updateRoute(id, route);
	}
	
	@DeleteMapping("/routes/{id}")
	public ResponseEntity<String> deleteRoute(@PathVariable Long id) {
		routeTripService.deleteRoute(id);
		return ResponseEntity.ok("Route deleted successfully");
	}
	
	@GetMapping("/trips")
	public List<TripResponseDto> getAllTrips() {
		return routeTripService.getAllTrips();
	}
	@GetMapping("/trips/{id}")
	public TripResponseDto getTripById(@PathVariable Long id) {
		return routeTripService.getTripById(id);
	}
	
	@PostMapping("/trips")
	public TripResponseDto addTrip(@RequestBody Trip trip) {
		return routeTripService.addTrip(trip);
	}
	
	@PutMapping("/trips/{id}")
	public TripResponseDto updateTrip(@PathVariable Long id, @RequestBody Trip trip) {
		return routeTripService.updateTrip(id, trip);
	}

	@DeleteMapping("/trips/{id}")
	public ResponseEntity<String> deleteTrip(@PathVariable Long id) {
		routeTripService.deleteTrip(id);
		return ResponseEntity.ok("Trip deleted successfully");
	}
	
	@GetMapping("/routes/search/{fromCity}/{toCity}")
	public List<RouteResponseDto> getRoutesByCities(
	        @PathVariable String fromCity,
	        @PathVariable String toCity) {

	    return routeTripService.getRoutesByCities(fromCity, toCity);
	}


	
	@GetMapping("/routes/from/{fromCity}")
	public List<RouteResponseDto> getRoutesByFromCity(
	        @PathVariable String fromCity) {

	    return routeTripService.getRoutesByFromCity(fromCity);
	}


	
	@GetMapping("/trips/route/{routeId}")
	public List<TripResponseDto> getTripsByRouteId(
	        @PathVariable Long routeId) {

	    return routeTripService.getTripsByRouteId(routeId);
	}


	
	@GetMapping("/trips/bus/{busId}")
	public List<TripResponseDto> getTripsByBusId(@PathVariable Long busId) {

	    return routeTripService.getTripsByBusId(busId);
	}


	
	@GetMapping("/trips/seats/{seats}")
	public List<TripResponseDto> getTripsByAvailableSeats(
	        @PathVariable Integer seats) {

	    return routeTripService.getTripsByAvailableSeats(seats);
	}
	

	@GetMapping("/routes/to/{toCity}")
	public List<RouteResponseDto> getRoutesByToCity(@PathVariable String toCity) {
	    return routeTripService.getRoutesByToCity(toCity);
	}


	@GetMapping("/routes/duration/{duration}")
	public List<RouteResponseDto> getRoutesByDuration(@PathVariable Integer duration) {
	    return routeTripService.getRoutesByDuration(duration);
	}

	@GetMapping("/trips/fare/{fare}")
	public List<TripResponseDto> getTripsByFare(@PathVariable BigDecimal fare) {
	    return routeTripService.getTripsByFare(fare);
	}


	
	@GetMapping("/trips/date/{date}")
	public List<TripResponseDto> getTripsByDate(@PathVariable LocalDate date) {
	    return routeTripService.getTripsByDate(date);
	}


	
	@GetMapping("/trips/departure/{time}")
	public List<TripResponseDto> getTripsByDepartureTime(
	        @PathVariable LocalDateTime time) {
	    return routeTripService.getTripsByDepartureTime(time);
	}
}
