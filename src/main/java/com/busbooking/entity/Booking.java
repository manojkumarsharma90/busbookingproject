package com.busbooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="bookings")
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long bookingId;
	
	@Column(nullable=false)
	 private Integer seatNumber;
	 
	 private boolean deleted = false;

	 public Long getBookingId() {
		 return bookingId;
	 }

	 public void setBookingId(Long bookingId) {
		 this.bookingId = bookingId;
	 }

	 public Integer getSeatNumber() {
		 return seatNumber;
	 }

	 public void setSeatNumber(Integer seatNumber) {
		 this.seatNumber = seatNumber;
	 }

	 public boolean isDeleted() {
		 return deleted;
	 }

	 public void setDeleted(boolean deleted) {
		 this.deleted = deleted;
	 }

	 @Override
	 public String toString() {
		return "Booking [bookingId=" + bookingId + ", seatNumber=" + seatNumber + ", deleted=" + deleted + "]";
	 }
	 
	 
	

}
