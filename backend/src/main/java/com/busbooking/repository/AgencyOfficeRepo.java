package com.busbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busbooking.entity.AgencyOffice;

public interface AgencyOfficeRepo extends JpaRepository<AgencyOffice, Long> {

    List<AgencyOffice> findByAgency_AgencyId(Long agencyId);

    long countByAgency_AgencyId(Long agencyId);

    Optional<AgencyOffice> findByOfficeMailIgnoreCase(String officeMail);

    List<AgencyOffice> findByOfficeContactPersonNameIgnoreCase(String name);

    void deleteByAgency_AgencyId(Long agencyId);
}