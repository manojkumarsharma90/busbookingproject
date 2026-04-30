package com.busbooking.dto;

import com.busbooking.entity.Bus;


public class BusMapper {

    public static BusDto toDto(Bus bus) {
        BusDto dto = new BusDto();
        dto.setBusId(bus.getBusId());
        dto.setRegistrationNumber(bus.getRegistrationNumber());
        dto.setType(bus.getType());
        dto.setCapacity(bus.getCapacity());

        if (bus.getOffice() != null) {
            dto.setOfficeId(bus.getOffice().getOfficeId());
        }

        return dto;
    }

    public static Bus toEntity(BusDto dto) {
        Bus bus = new Bus();
        bus.setRegistrationNumber(dto.getRegistrationNumber());
        bus.setType(dto.getType());
        bus.setCapacity(dto.getCapacity());
        return bus;
    }
}