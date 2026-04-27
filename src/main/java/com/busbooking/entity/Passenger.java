package com.busbooking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "passenger")

public class Passenger {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pId;

	private String pName;

	private Integer pAge;

	private String seatNo;

	@ManyToOne()
	@JoinColumn(name = "bookingId")
	private Booking trip;

	public Passenger() {

	}

	public Booking getTrip() {
		return trip;
	}

	public void setTrip(Booking trip) {
		this.trip = trip;
	}

	public Passenger(Integer pId, String pName, Integer pAge, String seatNo, Booking trip) {
		super();
		this.pId = pId;
		this.pName = pName;
		this.pAge = pAge;
		this.seatNo = seatNo;
		this.trip = trip;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public Integer getpAge() {
		return pAge;
	}

	public void setpAge(Integer pAge) {
		this.pAge = pAge;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

}
