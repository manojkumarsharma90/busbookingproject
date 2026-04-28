package com.busbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busbooking.entity.AgencyOffice;

public interface AgencyOfficeRepo extends JpaRepository<AgencyOffice, Long> {
	
	List<AgencyOffice> findByAgency_AgencyId(Long agencyId);
	

}
