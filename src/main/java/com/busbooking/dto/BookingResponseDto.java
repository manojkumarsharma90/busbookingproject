package com.busbooking.dto;

import java.time.LocalDateTime;
import java.util.List;

public class BookingResponseDto {

    private Long bookingId;
    private String status;
    private LocalDateTime bookingDate;

    private String customerName;
    private Integer totalSeats;

    private TripInfoDto trip;   
    private List<PassengerDto> passengers;
	public Long getBookingId() {
		return bookingId;
	}
	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public LocalDateTime getBookingDate() {
		return bookingDate;
	}
	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}
	public TripInfoDto getTrip() {
		return trip;
	}
	public void setTrip(TripInfoDto trip) {
		this.trip = trip;
	}
	public List<PassengerDto> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<PassengerDto> passengers) {
		this.passengers = passengers;
	}

}