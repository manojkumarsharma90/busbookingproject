package com.busbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Driver;

import java.util.List;
import java.util.Optional;

@Repository
public interface DriverRepo extends JpaRepository<Driver, Long> {

    List<Driver> findByOffice_OfficeId(Long officeId);

    List<Driver> findByNameContainingIgnoreCase(String name);

    Optional<Driver> findByLicenseNumber(String licenseNumber);

    Optional<Driver> findByPhone(String phone);
}