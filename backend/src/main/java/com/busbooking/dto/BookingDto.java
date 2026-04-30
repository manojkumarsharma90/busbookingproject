package com.busbooking.dto;

import java.util.List;

public class BookingDto {
	
	private Long tripId;
	
	private List<PassengerDto> passenger;
	
	
	public BookingDto() {
		super();
	}

	public BookingDto(Long tripId, List<PassengerDto> passenger) {
		super();
		this.tripId = tripId;
		this.passenger = passenger;
	}

	public Long getTripId() {
		return tripId;
	}

	public void setTripId(Long tripId) {
		this.tripId = tripId;
	}

	public List<PassengerDto> getPassenger() {
		return passenger;
	}

	public void setPassenger(List<PassengerDto> passenger) {
		this.passenger = passenger;
	}
}

