package com.busbooking.dto;

import com.busbooking.entity.Agency;

public class AgencyMapper {

    public static Agency toEntity(AgencyRequestDto dto) {
        Agency agency = new Agency();
        agency.setName(dto.getName());
        agency.setContactPersonName(dto.getContactPersonName());
        agency.setEmail(dto.getEmail());
        agency.setPhone(dto.getPhone());
        return agency;
    }

    public static AgencyResponseDto toDTO(Agency agency) {
        AgencyResponseDto dto = new AgencyResponseDto();
        dto.setAgencyId(agency.getAgencyId());
        dto.setName(agency.getName());
        dto.setContactPersonName(agency.getContactPersonName());
        dto.setEmail(agency.getEmail());
        dto.setPhone(agency.getPhone());
        return dto;
    }
}