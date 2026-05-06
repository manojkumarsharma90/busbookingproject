package com.busbooking.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "agency_offices")
public class AgencyOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long officeId;

    @ManyToOne
    @JoinColumn(name = "agency_id")
    private Agency agency;

    private String officeMail;

    private String officeContactPersonName;

    @Column(length = 10)
    private String officeContactNumber;

    @ManyToOne
    @JoinColumn(name = "office_address_id")
    private Address officeAddress;

    public AgencyOffice() {}

    public AgencyOffice(Long officeId, Agency agency, String officeMail,
                        String officeContactPersonName, String officeContactNumber,
                        Address officeAddress) {
        this.officeId = officeId;
        this.agency = agency;
        this.officeMail = officeMail;
        this.officeContactPersonName = officeContactPersonName;
        this.officeContactNumber = officeContactNumber;
        this.officeAddress = officeAddress;
    }

    public Long getOfficeId() { return officeId; }
    public void setOfficeId(Long officeId) { this.officeId = officeId; }

    public Agency getAgency() { return agency; }
    public void setAgency(Agency agency) { this.agency = agency; }

    public String getOfficeMail() { return officeMail; }
    public void setOfficeMail(String officeMail) { this.officeMail = officeMail; }

    public String getOfficeContactPersonName() { return officeContactPersonName; }
    public void setOfficeContactPersonName(String officeContactPersonName) {
        this.officeContactPersonName = officeContactPersonName;
    }

    public String getOfficeContactNumber() { return officeContactNumber; }
    public void setOfficeContactNumber(String officeContactNumber) {
        this.officeContactNumber = officeContactNumber;
    }

    public Address getOfficeAddress() { return officeAddress; }
    public void setOfficeAddress(Address officeAddress) { this.officeAddress = officeAddress; }
}
