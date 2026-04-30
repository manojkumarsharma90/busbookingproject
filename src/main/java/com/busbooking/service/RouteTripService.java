package com.busbooking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbooking.dto.BookingMapper;
import com.busbooking.dto.RouteMapper;
import com.busbooking.dto.RouteRequestDto;
import com.busbooking.dto.RouteResponseDto;
import com.busbooking.dto.TripRequestDto;
import com.busbooking.dto.TripResponseDto;
import com.busbooking.entity.Bus;
import com.busbooking.entity.Driver;
import com.busbooking.entity.Route;
import com.busbooking.entity.Trip;
import com.busbooking.repository.AddressRepo;
import com.busbooking.repository.BusRepo;
import com.busbooking.repository.DriverRepo;
import com.busbooking.repository.RouteRepo;
import com.busbooking.repository.TripRepo;
import com.busbooking.serviceInterface.IRouteTripService;


@Service
public class RouteTripService implements IRouteTripService{
	
	@Autowired
	private BookingMapper mapper;

	@Autowired
	private RouteRepo routeRepo;

	@Autowired
	private TripRepo tripRepo;

	@Autowired
	private BusRepo busRepo;

	@Autowired
	private DriverRepo driverRepo;
	
	@Autowired
	private RouteMapper routemapper;

	@Autowired
	private AddressRepo addressRepo;
	
	
	public List<RouteResponseDto> getRoutesByToCity(String toCity) {
	    List<Route> route= routeRepo.findByToCityIgnoreCase(toCity);
	    
	    return route.stream().map(routemapper::toDto).toList();
	}

	public List<RouteResponseDto> getRoutesByDuration(Integer duration) {
		List<Route> route=   routeRepo.findByDurationGreaterThan(duration);
		return route.stream().map(routemapper::toDto).toList();
	}

	public List<TripResponseDto> getTripsByFare(BigDecimal fare) {
	   
		List<Trip> trip=tripRepo.findByFareLessThan(fare);
		
		return trip.stream().map(mapper::mapTrip).toList();
		
	}

	public List<TripResponseDto> getTripsByDate(LocalDate date) {
	    List<Trip> trip= tripRepo.findByTripDate(date);
	    return trip.stream().map(mapper::mapTrip).toList();
	}

	public List<TripResponseDto> getTripsByDepartureTime(LocalDateTime time) {
		List<Trip> trip= tripRepo.findByDepartureTimeAfter(time);
		
		return trip.stream().map(mapper::mapTrip).toList();
	}

	public List<RouteResponseDto> getRoutesByCities(String fromCity, String toCity) {
	    List<Route> route= routeRepo.findByFromCityIgnoreCaseAndToCityIgnoreCase(fromCity, toCity);
	    
	    return route.stream().map(routemapper::toDto).toList();
	}

	public List<RouteResponseDto> getRoutesByFromCity(String fromCity) {
	    List<Route> route= routeRepo.findByFromCityIgnoreCase(fromCity);
	    return route.stream().map(routemapper::toDto).toList();
	}

	public List<TripResponseDto> getTripsByRouteId(Long routeId) {
		List<Trip> trip= tripRepo.findByRoute_RouteId(routeId);
		
		return trip.stream().map(mapper::mapTrip).toList();
		
	}

	public List<TripResponseDto> getTripsByBusId(Long busId) {
		List<Trip> trip= tripRepo.findByBus_BusId(busId);
		return trip.stream().map(mapper::mapTrip).toList();
	}

	public List<TripResponseDto> getTripsByAvailableSeats(Integer seats) {
		List<Trip> trip= tripRepo.findByAvailableSeatsGreaterThan(seats);
		return trip.stream().map(mapper::mapTrip).toList();
	}

	public List<RouteResponseDto> getAllRoutes() {
		List<Route> route= routeRepo.findAll();
		return route.stream().map(routemapper::toDto).toList();
	}

	public RouteResponseDto getRouteById(Long id) {
		Route route= routeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Route not found"));
		return routemapper.toDto(route);
	}

	public RouteResponseDto addRoute(RouteRequestDto route) {
		Route rRoute=routemapper.toEntity(route);
		Route sRoute= routeRepo.save(rRoute);
		return routemapper.toDto(sRoute);
	}

	public RouteResponseDto updateRoute(Long id, RouteRequestDto cRoute) {
		
		
		Route route=routemapper.toEntity(cRoute);

		Route existing = routeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Route not found"));

		existing.setFromCity(route.getFromCity());
		existing.setToCity(route.getToCity());
		existing.setBreakPoints(route.getBreakPoints());
		existing.setDuration(route.getDuration());

		Route sRoute= routeRepo.save(existing);
		
		return routemapper.toDto(sRoute);
	}

	public void deleteRoute(Long id) {
		routeRepo.deleteById(id);
	}



	public List<TripResponseDto> getAllTrips() {
		List<Trip> trip= tripRepo.findAll();
		return trip.stream().map(mapper::mapTrip).toList();
	}

	public TripResponseDto getTripById(Long id) {
		Trip trip= tripRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Trip not found"));
		return mapper.mapTrip(trip);
	}

	public TripResponseDto addTrip(Trip trip) {
		
		

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

		Trip sTrip= tripRepo.save(trip);
		
		return mapper.mapTrip(sTrip);
		
	}

	public TripResponseDto updateTrip(Long id, Trip trip) {

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

		Trip sTrip= tripRepo.save(existing);
		
		return mapper.mapTrip(sTrip);
	}

	public void deleteTrip(Long id) {
		tripRepo.deleteById(id);
	}
}
