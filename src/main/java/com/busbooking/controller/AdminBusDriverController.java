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

import com.busbooking.entity.Bus;
import com.busbooking.entity.Driver;
import com.busbooking.service.BusDriverService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/admin")
@CrossOrigin
@SecurityRequirement(name = "BearerAuth")
public class AdminBusDriverController {

	@Autowired
	private BusDriverService busDriverService;

	// BUS APIs

	// GET all buses
	@GetMapping("/buses")
	public List<Bus> getAllBuses() {
		return busDriverService.getAllBuses();
	}

	// GET bus by id
	@GetMapping("/buses/{id}")
	public Bus getBusById(@PathVariable Long id) {
		return busDriverService.getBusById(id);
	}

	// GET buses by office
	@GetMapping("/buses/office/{officeId}")
	public List<Bus> getBusesByOffice(@PathVariable Long officeId) {
		return busDriverService.getBusesByOffice(officeId);
	}

	// ADD bus
	@PostMapping("/buses")
	public Bus addBus(@RequestBody Bus bus) {
		return busDriverService.addBus(bus);
	}

	// UPDATE bus
	@PutMapping("/buses/{id}")
	public Bus updateBus(@PathVariable Long id, @RequestBody Bus bus) {
		return busDriverService.updateBus(id, bus);
	}

	// DELETE bus
	@DeleteMapping("/buses/{id}")
	public ResponseEntity<String> deleteBus(@PathVariable Long id) {
		busDriverService.deleteBus(id);
		return ResponseEntity.ok("Bus deleted successfully");
	}

	// DRIVER APIs

	// GET all drivers
	@GetMapping("/drivers")
	public List<Driver> getAllDrivers() {
		return busDriverService.getAllDrivers();
	}

	// GET driver by id
	@GetMapping("/drivers/{id}")
	public Driver getDriverById(@PathVariable Long id) {
		return busDriverService.getDriverById(id);
	}

	// GET drivers by office
	@GetMapping("/drivers/office/{officeId}")
	public List<Driver> getDriversByOffice(@PathVariable Long officeId) {
		return busDriverService.getDriversByOffice(officeId);
	}

	// ADD driver
	@PostMapping("/drivers")
	public Driver addDriver(@RequestBody Driver driver) {
		return busDriverService.addDriver(driver);
	}

	// UPDATE driver
	@PutMapping("/drivers/{id}")
	public Driver updateDriver(@PathVariable Long id, @RequestBody Driver driver) {
		return busDriverService.updateDriver(id, driver);
	}

	// DELETE driver
	@DeleteMapping("/drivers/{id}")
	public ResponseEntity<String> deleteDriver(@PathVariable Long id) {
		busDriverService.deleteDriver(id);
		return ResponseEntity.ok("Driver deleted successfully");
	}
}
