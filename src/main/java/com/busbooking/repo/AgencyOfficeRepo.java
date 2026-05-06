package com.busbooking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.AgencyOffice;

import java.util.List;

@Repository
public interface AgencyOfficeRepo extends JpaRepository<AgencyOffice, Long> {
	List<AgencyOffice> findByAgency_AgencyId(Long agencyId);
}
