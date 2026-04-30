package com.busbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Bus;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRepo extends JpaRepository<Bus, Long> {

    List<Bus> findByOffice_OfficeId(Long officeId);

    boolean existsByRegistrationNumber(String registrationNumber);

    List<Bus> findByTypeIgnoreCase(String type);

    List<Bus> findByCapacityGreaterThanEqual(Integer capacity);

    Optional<Bus> findByRegistrationNumber(String registrationNumber);
}