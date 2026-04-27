package com.busbooking.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "routes")

public class Route {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;
    private String fromCity;
    private String toCity;
    private Integer breakPoints;
    private Integer duration;
	private Long getRouteId() {
		return routeId;
	}
	private void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	private String getFromCity() {
		return fromCity;
	}
	private void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}
	private String getToCity() {
		return toCity;
	}
	private void setToCity(String toCity) {
		this.toCity = toCity;
	}
	private Integer getBreakPoints() {
		return breakPoints;
	}
	private void setBreakPoints(Integer breakPoints) {
		this.breakPoints = breakPoints;
	}
	private Integer getDuration() {
		return duration;
	}
	private void setDuration(Integer duration) {
		this.duration = duration;
	}
	public Route() {
		
	}
    
}
