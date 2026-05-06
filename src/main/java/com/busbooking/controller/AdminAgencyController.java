package com.busbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.entity.Agency;
import com.busbooking.entity.AgencyOffice;
import com.busbooking.service.AgencyService;

@RestController
@RequestMapping("/admin")
@CrossOrigin
public class AdminAgencyController {

	@Autowired
	private AgencyService agencyService;


	// ============================
	// AGENCY APIs
	// ============================

	// GET all agencies
	@GetMapping("/agencies")
	public List<Agency> getAllAgencies() {
		return agencyService.getAllAgencies();
	}

	// GET agency by id
	@GetMapping("/agencies/{id}")
	public Agency getAgencyById(@PathVariable Long id) {
		return agencyService.getAgencyById(id);
	}

	// ADD agency
	@PostMapping("/agencies")
	public Agency addAgency(@RequestBody Agency agency) {
		return agencyService.addAgency(agency);
	}

	// UPDATE agency
	@PutMapping("/agencies/{id}")
	public Agency updateAgency(@PathVariable Long id, @RequestBody Agency agency) {
		return agencyService.updateAgency(id, agency);
	}

	// DELETE agency
	@DeleteMapping("/agencies/{id}")
	public ResponseEntity<String> deleteAgency(@PathVariable Long id) {
		agencyService.deleteAgency(id);
		return ResponseEntity.ok("Agency deleted successfully");
	}


	// ============================
	// AGENCY OFFICE APIs
	// ============================

	// GET all offices
	@GetMapping("/agency-offices")
	public List<AgencyOffice> getAllOffices() {
		return agencyService.getAllOffices();
	}

	// GET office by id
	@GetMapping("/agency-offices/{id}")
	public AgencyOffice getOfficeById(@PathVariable Long id) {
		return agencyService.getOfficeById(id);
	}

	// GET offices by agency
	@GetMapping("/agency-offices/agency/{agencyId}")
	public List<AgencyOffice> getOfficesByAgency(@PathVariable Long agencyId) {
		return agencyService.getOfficesByAgency(agencyId);
	}

	// ADD office
	@PostMapping("/agency-offices")
	public AgencyOffice addOffice(@RequestBody AgencyOffice office) {
		return agencyService.addOffice(office);
	}

	// UPDATE office
	@PutMapping("/agency-offices/{id}")
	public AgencyOffice updateOffice(@PathVariable Long id, @RequestBody AgencyOffice office) {
		return agencyService.updateOffice(id, office);
	}

	// DELETE office
	@DeleteMapping("/agency-offices/{id}")
	public ResponseEntity<String> deleteOffice(@PathVariable Long id) {
		agencyService.deleteOffice(id);
		return ResponseEntity.ok("Office deleted successfully");
	}
}
