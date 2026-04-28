package com.busbooking.entity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;

	@ManyToOne()
	@JoinColumn(name = "trip_id")
	private Trip trip;

	private boolean deleted = false;

	@OneToMany(mappedBy = "booking")
	private Set<Passenger> pSet;
	
	@ManyToOne
	@JoinColumn(name="customerId")
   private Customer customer;
	
	public Booking() {

	}
	
	
	public Booking(Long bookingId, Integer seatNumber, Trip trip, boolean deleted, Set<Passenger> pSet) {

		this.bookingId = bookingId;
		this.trip = trip;
		this.deleted = deleted;
		this.pSet = pSet;
	}

	
	

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Set<Passenger> getpSet() {
		return pSet;
	}

	public void setpSet(Set<Passenger> pSet) {
		this.pSet = pSet;
	}

	
}
