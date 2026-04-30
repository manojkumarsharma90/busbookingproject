package com.busbooking.dto;

public class AgencyOfficeResponseDto {

	private Long officeId;
	private String officeMail;
	private String officeContactPersonName;
	private String officeContactNumber;

	private Long agencyId;
	private String agencyName;

	// optional
	private Long officeAddressId;

	public AgencyOfficeResponseDto() {
		super();
	}

	public Long getOfficeId() {
		return officeId;
	}

	public void setOfficeId(Long officeId) {
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

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public Long getOfficeAddressId() {
		return officeAddressId;
	}

	public void setOfficeAddressId(Long officeAddressId) {
		this.officeAddressId = officeAddressId;
	}
}