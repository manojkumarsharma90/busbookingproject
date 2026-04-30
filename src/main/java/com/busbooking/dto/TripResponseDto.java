package com.busbooking.dto;



import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class TripResponseDto {

	private Long tripId;

   
    public Long getTripId() {
		return tripId;
	}
	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}
	public Long getRouteId() {
		return routeId;
	}
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public Long getBusId() {
		return busId;
	}
	public void setBusId(Long busId) {
		this.busId = busId;
	}
	public String getBusNumber() {
		return busNumber;
	}
	public void setBusNumber(String busNumber) {
		this.busNumber = busNumber;
	}
	public String getBusType() {
		return busType;
	}
	public void setBusType(String busType) {
		this.busType = busType;
	}
	public LocalDateTime getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(LocalDateTime departureTime) {
		this.departureTime = departureTime;
	}
	public LocalDate getTripDate() {
		return tripDate;
	}
	public void setTripDate(LocalDate tripDate) {
		this.tripDate = tripDate;
	}
	public Integer getAvailableSeats() {
		return availableSeats;
	}
	public void setAvailableSeats(Integer availableSeats) {
		this.availableSeats = availableSeats;
	}
	public BigDecimal getFare() {
		return fare;
	}
	public void setFare(BigDecimal fare) {
		this.fare = fare;
	}
	private Long routeId;
    private String source;
    private String destination;

    // Bus details
    private Long busId;
    private String busNumber;
    private String busType;

   


    // Timing
    private LocalDateTime departureTime;
    private LocalDate tripDate;

    // Seats & Fare
    private Integer availableSeats;
    private BigDecimal fare;
}