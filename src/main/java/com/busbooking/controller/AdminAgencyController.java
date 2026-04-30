package com.busbooking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.dto.AgencyOfficeRequestDto;
import com.busbooking.dto.AgencyOfficeResponseDto;
import com.busbooking.dto.AgencyRequestDto;
import com.busbooking.dto.AgencyResponseDto;
import com.busbooking.entity.Agency;
import com.busbooking.entity.AgencyOffice;
import com.busbooking.service.AgencyService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/admin")
@CrossOrigin
@SecurityRequirement(name = "BearerAuth")
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
    
    // Search agency by name
    @GetMapping("/agencies/search")
    public AgencyResponseDto getAgencyByName(@RequestParam String name) {
        return agencyService.getAgencyByName(name);
    }
    
    // Check if agency exists
    @GetMapping("/agencies/exists")
    public boolean agencyExists(@RequestParam String name) {
        return agencyService.agencyExists(name);
    }
    
    // Get agency by email
    @GetMapping("/agencies/email/{email}")
    public AgencyResponseDto getAgencyByEmail(@PathVariable String email) {
        return agencyService.getAgencyByEmail(email);
    }
    
    // Count total agencies
    @GetMapping("/agencies/count")
    public long countAgencies() {
        return agencyService.countAgencies();
    }
    
    // Partial update (PATCH)
    @PatchMapping("/agencies/{id}")
    public AgencyResponseDto updateAgencyPartial(@PathVariable Long id,
                                                 @RequestBody AgencyRequestDto dto) {
        return agencyService.updateAgencyPartial(id, dto);
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

    // @GetMapping("/agency-offices/agency/{agencyId}")
    public List<AgencyOfficeResponseDto> getOfficesByAgency(@PathVariable Long agencyId) {
        return agencyService.getOfficesByAgency(agencyId);
    }

    // ADD office
    @PostMapping("/agency-offices")
    public AgencyOfficeResponseDto addOffice(@RequestBody AgencyOfficeRequestDto dto) {
        return agencyService.addOffice(dto);
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
    
    // Count offices by agency
    @GetMapping("/agency-offices/count/{agencyId}")
    public long countOfficesByAgency(@PathVariable Long agencyId) {
        return agencyService.countOfficesByAgency(agencyId);
    }
    
    // Get office by email
    @GetMapping("/agency-offices/email/{email}")
    public AgencyOfficeResponseDto getOfficeByEmail(@PathVariable String email) {
        return agencyService.getOfficeByEmail(email);
    }
    
    // Get office by contact person
    @GetMapping("/agency-offices/contact")
    public List<AgencyOfficeResponseDto> getByContactPerson(@RequestParam String name) {
        return agencyService.getByContactPerson(name);
    }
    
    // Bulk create offices
    @PostMapping("/agency-offices/bulk")
    public List<AgencyOfficeResponseDto> addOffices(
            @RequestBody List<AgencyOfficeRequestDto> dtos) {
        return agencyService.addOffices(dtos);
    }
    
    // Delete all offices of an agency
    @DeleteMapping("/agency-offices/agency/{agencyId}")
    public String deleteOfficesByAgency(@PathVariable Long agencyId) {
        agencyService.deleteOfficesByAgency(agencyId);
        return "All offices deleted";
    }
}

