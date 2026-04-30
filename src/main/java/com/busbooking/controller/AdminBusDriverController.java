package com.busbooking.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.busbooking.dto.BusDto;
import com.busbooking.dto.DriverDto;
import com.busbooking.service.BusDriverService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/admin")
@CrossOrigin
@SecurityRequirement(name = "BearerAuth")
public class AdminBusDriverController {

    private final BusDriverService busDriverService;

    public AdminBusDriverController(BusDriverService busDriverService) {
        this.busDriverService = busDriverService;
    }

    // =========================
    // BUS APIs - 9 APIs
    // =========================

    // 1. GET all buses
    @GetMapping("/buses")
    public List<BusDto> getAllBuses() {
        return busDriverService.getAllBuses();
    }

    // 2. GET bus by id
    @GetMapping("/buses/{id}")
    public BusDto getBusById(@PathVariable Long id) {
        return busDriverService.getBusById(id);
    }

    // 3. GET buses by office
    @GetMapping("/buses/office/{officeId}")
    public List<BusDto> getBusesByOffice(@PathVariable Long officeId) {
        return busDriverService.getBusesByOffice(officeId);
    }

    // 4. GET buses by type
    @GetMapping("/buses/type/{type}")
    public List<BusDto> getBusesByType(@PathVariable String type) {
        return busDriverService.getBusesByType(type);
    }

    // 5. GET buses by minimum capacity
    @GetMapping("/buses/capacity/{capacity}")
    public List<BusDto> getBusesByCapacity(@PathVariable Integer capacity) {
        return busDriverService.getBusesByCapacity(capacity);
    }

    // 6. GET bus by registration number
    @GetMapping("/buses/registration/{registrationNumber}")
    public BusDto getBusByRegistrationNumber(@PathVariable String registrationNumber) {
        return busDriverService.getBusByRegistrationNumber(registrationNumber);
    }

    // 7. ADD bus
    @PostMapping("/buses")
    public BusDto addBus(@RequestBody BusDto busDto) {
        return busDriverService.addBus(busDto);
    }

    // 8. UPDATE bus
    @PutMapping("/buses/{id}")
    public BusDto updateBus(@PathVariable Long id, @RequestBody BusDto busDto) {
        return busDriverService.updateBus(id, busDto);
    }

    // 9. DELETE bus
    @DeleteMapping("/buses/{id}")
    public ResponseEntity<String> deleteBus(@PathVariable Long id) {
        busDriverService.deleteBus(id);
        return ResponseEntity.ok("Bus deleted successfully");
    }

    // =========================
    // DRIVER APIs - 9 APIs
    // =========================

    // 10. GET all drivers
    @GetMapping("/drivers")
    public List<DriverDto> getAllDrivers() {
        return busDriverService.getAllDrivers();
    }

    // 11. GET driver by id
    @GetMapping("/drivers/{id}")
    public DriverDto getDriverById(@PathVariable Long id) {
        return busDriverService.getDriverById(id);
    }

    // 12. GET drivers by office
    @GetMapping("/drivers/office/{officeId}")
    public List<DriverDto> getDriversByOffice(@PathVariable Long officeId) {
        return busDriverService.getDriversByOffice(officeId);
    }

    // 13. GET drivers by name
    @GetMapping("/drivers/name/{name}")
    public List<DriverDto> getDriversByName(@PathVariable String name) {
        return busDriverService.getDriversByName(name);
    }

    // 14. GET driver by license number
    @GetMapping("/drivers/license/{licenseNumber}")
    public DriverDto getDriverByLicenseNumber(@PathVariable String licenseNumber) {
        return busDriverService.getDriverByLicenseNumber(licenseNumber);
    }

    // 15. GET driver by phone
    @GetMapping("/drivers/phone/{phone}")
    public DriverDto getDriverByPhone(@PathVariable String phone) {
        return busDriverService.getDriverByPhone(phone);
    }

    // 16. ADD driver
    @PostMapping("/drivers")
    public DriverDto addDriver(@RequestBody DriverDto driverDto) {
        return busDriverService.addDriver(driverDto);
    }

    // 17. UPDATE driver
    @PutMapping("/drivers/{id}")
    public DriverDto updateDriver(@PathVariable Long id, @RequestBody DriverDto driverDto) {
        return busDriverService.updateDriver(id, driverDto);
    }

    // 18. DELETE driver
    @DeleteMapping("/drivers/{id}")
    public ResponseEntity<String> deleteDriver(@PathVariable Long id) {
        busDriverService.deleteDriver(id);
        return ResponseEntity.ok("Driver deleted successfully");
    }
}