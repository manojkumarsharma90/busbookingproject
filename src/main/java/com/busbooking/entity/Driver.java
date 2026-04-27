package com.busbooking.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "drivers")

public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long driverId;
    private String licenseNumber;
    private String name;
    private String phone;
    @ManyToOne @JoinColumn(name = "office_id")
    private AgencyOffice office;
    @ManyToOne @JoinColumn(name = "address_id")
    private Address address;
    
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
	public AgencyOffice getOffice() {
		return office;
	}
	public void setOffice(AgencyOffice office) {
		this.office = office;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Driver(Long driverId, String licenseNumber, String name, String phone, AgencyOffice office,
			Address address) {
		super();
		this.driverId = driverId;
		this.licenseNumber = licenseNumber;
		this.name = name;
		this.phone = phone;
		this.office = office;
		this.address = address;
	}
	public Driver() {}
}
