package com.busbooking.entity;
import jakarta.persistence.*;

@Entity
@Table(name = "customers")

public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String name;
    private String email;
    private String phone;
    @ManyToOne @JoinColumn(name = "address_id")
    private Address address;
    
    
    
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public Customer(Long customerId, String name, String email, String phone, Address address) {
		super();
		this.customerId = customerId;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
	}
	public Customer() {}
}
