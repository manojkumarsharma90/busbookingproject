package com.busbooking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="agency_offices")

public class AgencyOffice {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer officeId;



private String officeMail;

private String officeContactPersonName;

@Column(length=10)
private String officeContactNumber;

@ManyToOne
@JoinColumn(name="agency_id")
private Agency agency;

@ManyToOne
@JoinColumn(name="office_address_id")
private Address officeAddress;

public AgencyOffice() {
	super();
}



public AgencyOffice(Integer officeId, String officeMail, 
	    String officeContactPersonName, String officeContactNumber, Agency agency) {

	    this.officeId = officeId;
	    this.officeMail = officeMail;
	    this.officeContactPersonName = officeContactPersonName;
	    this.officeContactNumber = officeContactNumber;
	    this.agency = agency;
	}


public Agency getAgency() {
	return agency;
}



public void setAgency(Agency agency) {
	this.agency = agency;
}



public Integer getOfficeId() {
	return officeId;
}

public void setOfficeId(Integer officeId) {
	this.officeId = officeId;
}


public String getOfficeMail() {
	return officeMail;
}

public void setOfficeMail(String officeMail) {
	this.officeMail = officeMail;
}

public String getOfficeContactPersonName() {
	return officeContactPersonName;
}

public void setOfficeContactPersonName(String officeContactPersonName) {
	this.officeContactPersonName = officeContactPersonName;
}

public String getOfficeContactNumber() {
	return officeContactNumber;
}

public void setOfficeContactNumber(String officeContactNumber) {
	this.officeContactNumber = officeContactNumber;
}
	

}
