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
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long agencyId;
	private String name;
	private String contactPersonName;
	private String email;
	private String phone;

}
