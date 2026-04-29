package com.busbooking.dto;



import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TripInfoDto {

    private Long tripId;
    private String fromCity;
    private String toCity;
    private LocalDateTime departureTime;
    private BigDecimal fare;

    public TripInfoDto() {}

    public TripInfoDto(Long tripId, String fromCity, String toCity,
                       LocalDateTime departureTime, BigDecimal fare) {
        this.tripId = tripId;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.departureTime = departureTime;
        this.fare = fare;
    }

    public Long getTripId() { return tripId; }
    public void setTripId(Long tripId) { this.tripId = tripId; }

    public String getFromCity() { return fromCity; }
    public void setFromCity(String fromCity) { this.fromCity = fromCity; }

    public String getToCity() { return toCity; }
    public void setToCity(String toCity) { this.toCity = toCity; }

    public LocalDateTime getDepartureTime() { return departureTime; }
    public void setDepartureTime(LocalDateTime departureTime) { this.departureTime = departureTime; }

    public BigDecimal getFare() { return fare; }
    public void setFare(BigDecimal bigDecimal) { this.fare = bigDecimal; }
}