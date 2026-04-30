package com.busbooking.dto;

public class AgencyOfficeRequestDto {

    private String officeMail;
    private String officeContactPersonName;
    private String officeContactNumber;

    private Long agencyId;
    private Long officeAddressId; // optional (since you have Address)

    public AgencyOfficeRequestDto() {
		super();
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

	public Long getOfficeAddressId() {
		return officeAddressId;
	}

	public void setOfficeAddressId(Long officeAddressId) {
		this.officeAddressId = officeAddressId;
	}
}