package com.busbooking.serviceInterface;

import java.util.List;
import com.busbooking.dto.BusDto;
import com.busbooking.dto.DriverDto;

public interface IBusDriverService {

    // BUS APIs

    List<BusDto> getAllBuses();

    BusDto getBusById(Long id);

    List<BusDto> getBusesByOffice(Long officeId);

    List<BusDto> getBusesByType(String type);

    List<BusDto> getBusesByCapacity(Integer capacity);

    BusDto getBusByRegistrationNumber(String registrationNumber);

    BusDto addBus(BusDto dto);

    BusDto updateBus(Long id, BusDto dto);

    void deleteBus(Long id);


    // DRIVER APIs

    List<DriverDto> getAllDrivers();

    DriverDto getDriverById(Long id);

    List<DriverDto> getDriversByOffice(Long officeId);

    List<DriverDto> getDriversByName(String name);

    DriverDto getDriverByLicenseNumber(String licenseNumber);

    DriverDto getDriverByPhone(String phone);

    DriverDto addDriver(DriverDto dto);

    DriverDto updateDriver(Long id, DriverDto dto);

    void deleteDriver(Long id);
}