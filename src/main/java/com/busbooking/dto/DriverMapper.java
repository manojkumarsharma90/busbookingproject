package com.busbooking.dto;



import com.busbooking.dto.DriverDto;
import com.busbooking.entity.Driver;

public class DriverMapper {

    public static DriverDto toDto(Driver driver) {
        DriverDto dto = new DriverDto();
        dto.setDriverId(driver.getDriverId());
        dto.setLicenseNumber(driver.getLicenseNumber());
        dto.setName(driver.getName());
        dto.setPhone(driver.getPhone());

        if (driver.getOffice() != null) {
            dto.setOfficeId(driver.getOffice().getOfficeId());
        }

        if (driver.getAddress() != null) {
            dto.setAddressId(driver.getAddress().getAddressId());
        }

        return dto;
    }

    public static Driver toEntity(DriverDto dto) {
        Driver driver = new Driver();
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());
        return driver;
    }
}
