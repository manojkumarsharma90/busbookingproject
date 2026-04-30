package com.busbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbooking.dto.BusDto;
import com.busbooking.dto.DriverDto;
import com.busbooking.dto.BusMapper;
import com.busbooking.dto.DriverMapper;
import com.busbooking.entity.Address;
import com.busbooking.entity.AgencyOffice;
import com.busbooking.entity.Bus;
import com.busbooking.entity.Driver;
import com.busbooking.exception.DuplicateResourceException;
import com.busbooking.exception.ResourceNotFoundException;
import com.busbooking.repository.AddressRepo;
import com.busbooking.repository.AgencyOfficeRepo;
import com.busbooking.repository.BusRepo;
import com.busbooking.repository.DriverRepo;
import com.busbooking.serviceInterface.IBusDriverService;

@Service
public class BusDriverService implements IBusDriverService {

    @Autowired
    private BusRepo busRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private AgencyOfficeRepo officeRepo;

    @Autowired
    private AddressRepo addressRepo;

    // BUS APIs

    public List<BusDto> getAllBuses() {
        return busRepo.findAll()
                .stream()
                .map(BusMapper::toDto)
                .toList();
    }

    public BusDto getBusById(Long id) {
        Bus bus = busRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id " + id));

        return BusMapper.toDto(bus);
    }

    public List<BusDto> getBusesByOffice(Long officeId) {
        return busRepo.findByOffice_OfficeId(officeId)
                .stream()
                .map(BusMapper::toDto)
                .toList();
    }

    public List<BusDto> getBusesByType(String type) {
        return busRepo.findByTypeIgnoreCase(type)
                .stream()
                .map(BusMapper::toDto)
                .toList();
    }

    public List<BusDto> getBusesByCapacity(Integer capacity) {
        return busRepo.findByCapacityGreaterThanEqual(capacity)
                .stream()
                .map(BusMapper::toDto)
                .toList();
    }

    public BusDto getBusByRegistrationNumber(String registrationNumber) {
        Bus bus = busRepo.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Bus not found with registration number " + registrationNumber));

        return BusMapper.toDto(bus);
    }

    public BusDto addBus(BusDto dto) {

        if (busRepo.existsByRegistrationNumber(dto.getRegistrationNumber())) {
            throw new DuplicateResourceException(
                    "Bus already exists with registration number " + dto.getRegistrationNumber());
        }

        Bus bus = BusMapper.toEntity(dto);

        if (dto.getOfficeId() != null) {
            AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Office not found with id " + dto.getOfficeId()));
            bus.setOffice(office);
        }

        Bus saved = busRepo.save(bus);
        return BusMapper.toDto(saved);
    }

    public BusDto updateBus(Long id, BusDto dto) {
        Bus existing = busRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id " + id));

        existing.setRegistrationNumber(dto.getRegistrationNumber());
        existing.setType(dto.getType());
        existing.setCapacity(dto.getCapacity());

        if (dto.getOfficeId() != null) {
            AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Office not found with id " + dto.getOfficeId()));
            existing.setOffice(office);
        }

        Bus updated = busRepo.save(existing);
        return BusMapper.toDto(updated);
    }

    public void deleteBus(Long id) {
        Bus bus = busRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bus not found with id " + id));

        busRepo.delete(bus);
    }

    // DRIVER APIs

    public List<DriverDto> getAllDrivers() {
        return driverRepo.findAll()
                .stream()
                .map(DriverMapper::toDto)
                .toList();
    }

    public DriverDto getDriverById(Long id) {
        Driver driver = driverRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id " + id));

        return DriverMapper.toDto(driver);
    }

    public List<DriverDto> getDriversByOffice(Long officeId) {
        return driverRepo.findByOffice_OfficeId(officeId)
                .stream()
                .map(DriverMapper::toDto)
                .toList();
    }

    public List<DriverDto> getDriversByName(String name) {
        return driverRepo.findByNameContainingIgnoreCase(name)
                .stream()
                .map(DriverMapper::toDto)
                .toList();
    }

    public DriverDto getDriverByLicenseNumber(String licenseNumber) {
        Driver driver = driverRepo.findByLicenseNumber(licenseNumber)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Driver not found with license number " + licenseNumber));

        return DriverMapper.toDto(driver);
    }

    public DriverDto getDriverByPhone(String phone) {
        Driver driver = driverRepo.findByPhone(phone)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Driver not found with phone " + phone));

        return DriverMapper.toDto(driver);
    }

    public DriverDto addDriver(DriverDto dto) {
        Driver driver = DriverMapper.toEntity(dto);

        if (dto.getOfficeId() != null) {
            AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Office not found with id " + dto.getOfficeId()));
            driver.setOffice(office);
        }

        if (dto.getAddressId() != null) {
            Address address = addressRepo.findById(dto.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Address not found with id " + dto.getAddressId()));
            driver.setAddress(address);
        }

        Driver saved = driverRepo.save(driver);
        return DriverMapper.toDto(saved);
    }

    public DriverDto updateDriver(Long id, DriverDto dto) {
        Driver existing = driverRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id " + id));

        existing.setLicenseNumber(dto.getLicenseNumber());
        existing.setName(dto.getName());
        existing.setPhone(dto.getPhone());

        if (dto.getOfficeId() != null) {
            AgencyOffice office = officeRepo.findById(dto.getOfficeId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Office not found with id " + dto.getOfficeId()));
            existing.setOffice(office);
        }

        if (dto.getAddressId() != null) {
            Address address = addressRepo.findById(dto.getAddressId())
                    .orElseThrow(() -> new ResourceNotFoundException(
                            "Address not found with id " + dto.getAddressId()));
            existing.setAddress(address);
        }

        Driver updated = driverRepo.save(existing);
        return DriverMapper.toDto(updated);
    }

    public void deleteDriver(Long id) {
        Driver driver = driverRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found with id " + id));

        driverRepo.delete(driver);
    }
}