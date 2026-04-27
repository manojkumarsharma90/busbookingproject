package com.busbooking.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity @Table(name = "trips")
public class Trip {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tripId;

    @ManyToOne @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne @JoinColumn(name = "bus_id")
    private Bus bus;

    @ManyToOne @JoinColumn(name = "boarding_address_id")
    private Address boardingAddress;

    @ManyToOne @JoinColumn(name = "dropping_address_id")
    private Address droppingAddress;

    @ManyToOne @JoinColumn(name = "driver1_driver_id")
    private Driver driver1;

    @ManyToOne @JoinColumn(name = "driver2_driver_id")
    private Driver driver2;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private LocalDateTime tripDate;
    private Integer availableSeats;
    private BigDecimal fare;
    
        public Trip() {
		// TODO Auto-generated constructor stub
	}

		public Long getTripId() {
			return tripId;
		}

		public void setTripId(Long tripId) {
			this.tripId = tripId;
		}

		public Route getRoute() {
			return route;
		}

		public void setRoute(Route route) {
			this.route = route;
		}

		public Bus getBus() {
			return bus;
		}

		public void setBus(Bus bus) {
			this.bus = bus;
		}

		public Address getBoardingAddress() {
			return boardingAddress;
		}

		public void setBoardingAddress(Address boardingAddress) {
			this.boardingAddress = boardingAddress;
		}

		public Address getDroppingAddress() {
			return droppingAddress;
		}

		public void setDroppingAddress(Address droppingAddress) {
			this.droppingAddress = droppingAddress;
		}

		public Driver getDriver1() {
			return driver1;
		}

		public void setDriver1(Driver driver1) {
			this.driver1 = driver1;
		}

		public Driver getDriver2() {
			return driver2;
		}

		public void setDriver2(Driver driver2) {
			this.driver2 = driver2;
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

		public LocalDateTime getTripDate() {
			return tripDate;
		}

		public void setTripDate(LocalDateTime tripDate) {
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

		public Trip(Long tripId, Route route, Bus bus, Address boardingAddress, Address droppingAddress, Driver driver1,
				Driver driver2, LocalDateTime departureTime, LocalDateTime arrivalTime, LocalDateTime tripDate,
				Integer availableSeats, BigDecimal fare) {
			super();
			this.tripId = tripId;
			this.route = route;
			this.bus = bus;
			this.boardingAddress = boardingAddress;
			this.droppingAddress = droppingAddress;
			this.driver1 = driver1;
			this.driver2 = driver2;
			this.departureTime = departureTime;
			this.arrivalTime = arrivalTime;
			this.tripDate = tripDate;
			this.availableSeats = availableSeats;
			this.fare = fare;
		}
	
}
