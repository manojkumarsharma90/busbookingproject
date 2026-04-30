package com.busbooking.entity;
import jakarta.persistence.*;


@Entity @Table(name = "routes")

public class Route {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long routeId;
    private String fromCity;
    private String toCity;
    private Integer breakPoints;
    private Integer duration;
public Route() {
		
	}
    public Route(Long routeId, String fromCity, String toCity, Integer breakPoints, Integer duration) {
		super();
		this.routeId = routeId;
		this.fromCity = fromCity;
		this.toCity = toCity;
		this.breakPoints = breakPoints;
		this.duration = duration;
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public String getFromCity() {
		return fromCity;
	}

	

	public void setFromCity(String fromCity) {
		this.fromCity = fromCity;
	}

	public String getToCity() {
		return toCity;
	}

	public void setToCity(String toCity) {
		this.toCity = toCity;
	}

	public Integer getBreakPoints() {
		return breakPoints;
	}

	public void setBreakPoints(Integer breakPoints) {
		this.breakPoints = breakPoints;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	
    
}
