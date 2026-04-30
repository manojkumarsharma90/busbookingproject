package com.busbooking.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class TripRequestDto {

    @NotNull(message = "Route ID is required")
    private Long routeId;

    @NotNull(message = "Bus ID is required")
    private Long busId;

    @NotNull(message = "Boarding address ID is required")
    private Long boardingAddressId;

    @NotNull(message = "Dropping address ID is required")
    private Long droppingAddressId;

    @NotNull(message = "Driver1 ID is required")
    private Long driver1Id;

    private Long driver2Id; // optional

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @NotNull(message = "Trip date is required")
    private LocalDate tripDate;

    @NotNull(message = "Available seats required")
    @Min(value = 1, message = "Seats must be at least 1")
    private Integer availableSeats;

    @NotNull(message = "Fare is required")
    @Min(value = 1, message = "Fare must be positive")
    private BigDecimal fare;

    // =====================
    // Getters & Setters
    // =====================

    public Long getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId = routeId;
    }

    public Long getBusId() {
        return busId;
    }

    public void setBusId(Long busId) {
        this.busId = busId;
    }

    public Long getBoardingAddressId() {
        return boardingAddressId;
    }

    public void setBoardingAddressId(Long boardingAddressId) {
        this.boardingAddressId = boardingAddressId;
    }

    public Long getDroppingAddressId() {
        return droppingAddressId;
    }

    public void setDroppingAddressId(Long droppingAddressId) {
        this.droppingAddressId = droppingAddressId;
    }

    public Long getDriver1Id() {
        return driver1Id;
    }

    public void setDriver1Id(Long driver1Id) {
        this.driver1Id = driver1Id;
    }

    public Long getDriver2Id() {
        return driver2Id;
    }

    public void setDriver2Id(Long driver2Id) {
        this.driver2Id = driver2Id;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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
}