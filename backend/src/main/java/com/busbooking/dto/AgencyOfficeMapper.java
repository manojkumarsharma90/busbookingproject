package com.busbooking.dto;

import com.busbooking.entity.*;

public class AgencyOfficeMapper {

    public static AgencyOfficeResponseDto toDTO(AgencyOffice office) {
        AgencyOfficeResponseDto dto = new AgencyOfficeResponseDto();

        dto.setOfficeId(office.getOfficeId());
        dto.setOfficeMail(office.getOfficeMail());
        dto.setOfficeContactPersonName(office.getOfficeContactPersonName());
        dto.setOfficeContactNumber(office.getOfficeContactNumber());

        if (office.getAgency() != null) {
            dto.setAgencyId(office.getAgency().getAgencyId());
            dto.setAgencyName(office.getAgency().getName());
        }

        return dto;
    }
}