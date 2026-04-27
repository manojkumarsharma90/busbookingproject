package com.busbooking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="agency_offices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgencyOffice {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer officeId;

@ManyToOne()
@JoinColumn(name="agencyId")
private Agency agencyId;

private String officeMail;

private String officeContactPersonName;

private String officeContactNumber;
	
}
