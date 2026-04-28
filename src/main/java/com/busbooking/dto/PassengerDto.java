package com.busbooking.dto;

public class PassengerDto {
	
	private String name;
	
	private Integer age;
	
	private String seatNo;
	
	private String gender;

	public PassengerDto() {
		super();
	}

	public PassengerDto(String name, Integer age, String seatNo, String gender) {
		super();
		this.name = name;
		this.age = age;
		this.seatNo = seatNo;
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSeatNo() {
		return seatNo;
	}

	public void setSeatNo(String seatNo) {
		this.seatNo = seatNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
}
