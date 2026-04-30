package com.busbooking.serviceInterface;

import java.util.List;

import com.busbooking.dto.AgencyOfficeRequestDto;
import com.busbooking.dto.AgencyOfficeResponseDto;
import com.busbooking.dto.AgencyRequestDto;
import com.busbooking.dto.AgencyResponseDto;
import com.busbooking.entity.Agency;
import com.busbooking.entity.AgencyOffice;

public interface IAgencyService {

	// AGENCY

	List<AgencyResponseDto> getAllAgencies();

	AgencyResponseDto getAgencyById(Long id);

	AgencyResponseDto addAgency(AgencyRequestDto dto);
	
	AgencyResponseDto updateAgency(Long id, AgencyRequestDto dto);

	void deleteAgency(Long id);

	AgencyResponseDto getAgencyByName(String name);

	boolean agencyExists(String name);

	AgencyResponseDto getAgencyByEmail(String email);

	long countAgencies();

	AgencyResponseDto updateAgencyPartial(Long id, AgencyRequestDto dto);

	// AGENCY OFFICE

	List<AgencyOfficeResponseDto> getAllOffices();

    AgencyOfficeResponseDto getOfficeById(Long id);
	
	List<AgencyOfficeResponseDto> getOfficesByAgency(Long agencyId);

	AgencyOfficeResponseDto addOffice(AgencyOfficeRequestDto dto);

	AgencyOfficeResponseDto updateOffice(Long id, AgencyOfficeRequestDto dto);

	void deleteOffice(Long id);

	long countOfficesByAgency(Long agencyId);

	AgencyOfficeResponseDto getOfficeByEmail(String email);

	List<AgencyOfficeResponseDto> getByContactPerson(String name);

	List<AgencyOfficeResponseDto> addOffices(List<AgencyOfficeRequestDto> dtos);

	void deleteOfficesByAgency(Long agencyId);
}