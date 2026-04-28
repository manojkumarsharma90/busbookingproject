package com.busbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbooking.entity.AgencyOffice;
import com.busbooking.entity.Bus;
import com.busbooking.entity.Driver;
import com.busbooking.repository.AgencyOfficeRepo;
import com.busbooking.repository.BusRepo;
import com.busbooking.repository.DriverRepo;

@Service
public class BusDriverService {

	@Autowired
	private BusRepo busRepo;

	@Autowired
	private DriverRepo driverRepo;

	@Autowired
	private AgencyOfficeRepo officeRepo;

	// BUS

	public List<Bus> getAllBuses() {
		return busRepo.findAll();
	}

	public Bus getBusById(Long id) {
		return busRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Bus not found"));
	}

	public List<Bus> getBusesByOffice(Long officeId) {
		return busRepo.findByOffice_OfficeId(officeId);
	}

	public Bus addBus(Bus bus) {

		if (bus.getOffice() != null && bus.getOffice().getOfficeId() != null) {
			AgencyOffice office = officeRepo.findById(bus.getOffice().getOfficeId())
					.orElseThrow(() -> new RuntimeException("Office not found"));
			bus.setOffice(office);
		}

		return busRepo.save(bus);
	}

	public Bus updateBus(Long id, Bus bus) {

		Bus existing = busRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Bus not found"));

		existing.setRegistrationNumber(bus.getRegistrationNumber());
		existing.setCapacity(bus.getCapacity());
		existing.setType(bus.getType());

		if (bus.getOffice() != null && bus.getOffice().getOfficeId() != null) {
			AgencyOffice office = officeRepo.findById(bus.getOffice().getOfficeId())
					.orElseThrow(() -> new RuntimeException("Office not found"));
			existing.setOffice(office);
		}

		return busRepo.save(existing);
	}

	public void deleteBus(Long id) {
		busRepo.deleteById(id);
	}

	// DRIVER

	public List<Driver> getAllDrivers() {
		return driverRepo.findAll();
	}

	public Driver getDriverById(Long id) {
		return driverRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Driver not found"));
	}

	public List<Driver> getDriversByOffice(Long officeId) {
		return driverRepo.findByOffice_OfficeId(officeId);
	}

	public Driver addDriver(Driver driver) {

		if (driver.getOffice() != null && driver.getOffice().getOfficeId() != null) {
			AgencyOffice office = officeRepo.findById(driver.getOffice().getOfficeId())
					.orElseThrow(() -> new RuntimeException("Office not found"));
			driver.setOffice(office);
		}

		return driverRepo.save(driver);
	}

	public Driver updateDriver(Long id, Driver driver) {

		Driver existing = driverRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Driver not found"));

		existing.setLicenseNumber(driver.getLicenseNumber());
		existing.setName(driver.getName());
		existing.setPhone(driver.getPhone());

		if (driver.getOffice() != null && driver.getOffice().getOfficeId() != null) {
			AgencyOffice office = officeRepo.findById(driver.getOffice().getOfficeId())
					.orElseThrow(() -> new RuntimeException("Office not found"));
			existing.setOffice(office);
		}

		return driverRepo.save(existing);
	}

	public void deleteDriver(Long id) {
		driverRepo.deleteById(id);
	}
}
