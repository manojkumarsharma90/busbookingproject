package com.busbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbooking.entity.Agency;
import com.busbooking.entity.AgencyOffice;
import com.busbooking.repo.AgencyOfficeRepo;
import com.busbooking.repo.AgencyRepo;

@Service
public class AgencyService {

	@Autowired
	private AgencyRepo agencyRepo;

	@Autowired
	private AgencyOfficeRepo officeRepo;


	// ============================
	// AGENCY
	// ============================

	public List<Agency> getAllAgencies() {
		return agencyRepo.findAll();
	}

	public Agency getAgencyById(Long id) {
		return agencyRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Agency not found"));
	}

	public Agency addAgency(Agency agency) {
		return agencyRepo.save(agency);
	}

	public Agency updateAgency(Long id, Agency agency) {

		Agency existing = agencyRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Agency not found"));

		existing.setName(agency.getName());
		existing.setContactPersonName(agency.getContactPersonName());
		existing.setEmail(agency.getEmail());
		existing.setPhone(agency.getPhone());

		return agencyRepo.save(existing);
	}

	public void deleteAgency(Long id) {
		agencyRepo.deleteById(id);
	}


	// ============================
	// AGENCY OFFICE
	// ============================

	public List<AgencyOffice> getAllOffices() {
		return officeRepo.findAll();
	}

	public AgencyOffice getOfficeById(Long id) {
		return officeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Office not found"));
	}

	public List<AgencyOffice> getOfficesByAgency(Long agencyId) {
		return officeRepo.findByAgency_AgencyId(agencyId);
	}

	public AgencyOffice addOffice(AgencyOffice office) {

		// resolve the agency from DB
		if (office.getAgency() != null && office.getAgency().getAgencyId() != null) {
			Agency agency = agencyRepo.findById(office.getAgency().getAgencyId())
					.orElseThrow(() -> new RuntimeException("Agency not found"));
			office.setAgency(agency);
		}

		return officeRepo.save(office);
	}

	public AgencyOffice updateOffice(Long id, AgencyOffice office) {

		AgencyOffice existing = officeRepo.findById(id)
				.orElseThrow(() -> new RuntimeException("Office not found"));

		existing.setOfficeMail(office.getOfficeMail());
		existing.setOfficeContactPersonName(office.getOfficeContactPersonName());
		existing.setOfficeContactNumber(office.getOfficeContactNumber());

		if (office.getAgency() != null && office.getAgency().getAgencyId() != null) {
			Agency agency = agencyRepo.findById(office.getAgency().getAgencyId())
					.orElseThrow(() -> new RuntimeException("Agency not found"));
			existing.setAgency(agency);
		}

		return officeRepo.save(existing);
	}

	public void deleteOffice(Long id) {
		officeRepo.deleteById(id);
	}
}
