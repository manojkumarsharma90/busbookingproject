package com.busbooking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Bus;

import java.util.List;

@Repository
public interface BusRepo extends JpaRepository<Bus, Long> {
	List<Bus> findByOffice_OfficeId(Long officeId);
	boolean existsByRegistrationNumber(String registrationNumber);
}
