package com.busbooking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "agencies")

public class Agency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long agencyId;
	private String name;
	private String contactPersonName;
	private String email;
	private String phone;
	
	public Agency() {
		
	}
	public Agency(Long agencyId, String name, String contactPersonName, String email, String phone) {
	
		this.agencyId = agencyId;
		this.name = name;
		this.contactPersonName = contactPersonName;
		this.email = email;
		this.phone = phone;
	}
	
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContactPersonName() {
		return contactPersonName;
	}
	public void setContactPersonName(String contactPersonName) {
		this.contactPersonName = contactPersonName;
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
	
	

}
