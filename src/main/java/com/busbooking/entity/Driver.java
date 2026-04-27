package com.busbooking.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "drivers")

public class Driver {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;
    private String licenseNumber;
    private String name;
    private String phone;
	public Long getDriverId() {
		return driverId;
	}
	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}
	public String getLicenseNumber() {
		return licenseNumber;
	}
	public void setLicenseNumber(String licenseNumber) {
		this.licenseNumber = licenseNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Driver(Long driverId, String licenseNumber, String name, String phone) {
		super();
		this.driverId = driverId;
		this.licenseNumber = licenseNumber;
		this.name = name;
		this.phone = phone;
	}
	public Driver() {}
}
