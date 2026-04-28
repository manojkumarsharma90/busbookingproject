package com.busbooking.entity;
import jakarta.persistence.*;
import lombok.*;

@Entit
@Table(name = "buses")
public class Bus {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busId;
    private String registrationNumber;
    private String type;
    private Integer capacity;
	public Long getBusId() {
		return busId;
	}
	public void setBusId(Long busId) {
		this.busId = busId;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	public Bus(Long busId, String registrationNumber, String type, Integer capacity) {
		super();
		this.busId = busId;
		this.registrationNumber = registrationNumber;
		this.type = type;
		this.capacity = capacity;
	}
    public Bus() {}
}
