package com.busbooking.dto;

import org.springframework.stereotype.Component;

import com.busbooking.entity.Route;

@Component
public class RouteMapper {

	
    
    public RouteResponseDto toDto(Route route) {

        if (route == null) {
            return null;
        }

        RouteResponseDto dto = new RouteResponseDto();
        dto.setFromCity(route.getFromCity());
        dto.setToCity(route.getToCity());
        dto.setBreakPoints(route.getBreakPoints());
        dto.setDuration(route.getDuration());

        

        return dto;
    }
    
    public Route toEntity(RouteRequestDto dto) {

        if (dto == null) {
            return null;
        }

        Route route = new Route();

        route.setFromCity(dto.getFromCity());
        route.setToCity(dto.getToCity());
        route.setBreakPoints(dto.getBreakPoints());
        route.setDuration(dto.getDuration());

        return route;
    }
    
    
    
    
}
