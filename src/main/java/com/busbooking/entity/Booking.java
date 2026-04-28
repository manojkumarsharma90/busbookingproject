package com.busbooking.entity;

<<<<<<< HEAD
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
=======
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
>>>>>>> 96e2888c64ecce805fd46986cef4bbda7dce02e8

@Entity
@Table(name = "bookings")
public class Booking {
<<<<<<< HEAD
	
	
	public Booking() {
	}

	public Booking(Long bookingId, Trip trip, Customer customer, Integer seatNumber, BookingStatus status,
			boolean deleted, LocalDateTime bookingDate) {
		this.bookingId = bookingId;
		this.trip = trip;
		this.customer = customer;
		this.seatNumber = seatNumber;
		this.status = status;
		this.deleted = deleted;
		this.bookingDate = bookingDate;
	}
=======

	
>>>>>>> 96e2888c64ecce805fd46986cef4bbda7dce02e8

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;

<<<<<<< HEAD
	@ManyToOne
	@JoinColumn(name = "trip_id")
	private Trip trip;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Column(nullable = false)
	private Integer seatNumber;

	@Enumerated(EnumType.STRING)
	@Column(length=20)
	private BookingStatus status = BookingStatus.Available;

	private boolean deleted = false;

	private LocalDateTime bookingDate;

	@OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Passenger> passengers = new ArrayList<>();

	

	public Long getBookingId() {
		return bookingId;
	}

	public void setBookingId(Long bookingId) {
		this.bookingId = bookingId;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getSeatNumber() {
		return seatNumber;
	}

	public void setSeatNumber(Integer seatNumber) {
		this.seatNumber = seatNumber;
	}

	public BookingStatus getStatus() {
		return status;
	}

	public void setStatus(BookingStatus status) {
		this.status = status;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public LocalDateTime getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDateTime bookingDate) {
		this.bookingDate = bookingDate;
	}

	public List<Passenger> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Passenger> passengers) {
		this.passengers = passengers;
	}
=======
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

	
>>>>>>> 96e2888c64ecce805fd46986cef4bbda7dce02e8
}
