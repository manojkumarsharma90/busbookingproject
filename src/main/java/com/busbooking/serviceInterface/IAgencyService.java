package com.busbooking.serviceInterface;

import java.util.List;

import com.busbooking.dto.AgencyOfficeRequestDto;
import com.busbooking.dto.AgencyOfficeResponseDto;
import com.busbooking.dto.AgencyRequestDto;
import com.busbooking.dto.AgencyResponseDto;
import com.busbooking.entity.Agency;
import com.busbooking.entity.AgencyOffice;

public interface IAgencyService {

    // ============================
    // AGENCY
    // ============================

    List<Agency> getAllAgencies();

    Agency getAgencyById(Long id);

    Agency addAgency(Agency agency);

    Agency updateAgency(Long id, Agency agency);

    void deleteAgency(Long id);

    AgencyResponseDto getAgencyByName(String name);

    boolean agencyExists(String name);

    AgencyResponseDto getAgencyByEmail(String email);

    long countAgencies();

    AgencyResponseDto updateAgencyPartial(Long id, AgencyRequestDto dto);

    // ============================
    // AGENCY OFFICE
    // ============================

    List<AgencyOffice> getAllOffices();

    AgencyOffice getOfficeById(Long id);

    List<AgencyOfficeResponseDto> getOfficesByAgency(Long agencyId);

    AgencyOfficeResponseDto addOffice(AgencyOfficeRequestDto dto);

    AgencyOffice updateOffice(Long id, AgencyOffice office);

    void deleteOffice(Long id);

    long countOfficesByAgency(Long agencyId);

    AgencyOfficeResponseDto getOfficeByEmail(String email);

    List<AgencyOfficeResponseDto> getByContactPerson(String name);

    List<AgencyOfficeResponseDto> addOffices(List<AgencyOfficeRequestDto> dtos);

    void deleteOfficesByAgency(Long agencyId);
}