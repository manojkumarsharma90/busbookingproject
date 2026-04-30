package com.busbooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.busbooking.dto.AgencyMapper;
import com.busbooking.dto.AgencyOfficeMapper;
import com.busbooking.dto.AgencyOfficeRequestDto;
import com.busbooking.dto.AgencyOfficeResponseDto;
import com.busbooking.dto.AgencyRequestDto;
import com.busbooking.dto.AgencyResponseDto;
import com.busbooking.entity.Agency;
import com.busbooking.entity.AgencyOffice;
import com.busbooking.repository.AgencyOfficeRepo;
import com.busbooking.repository.AgencyRepo;
import com.busbooking.serviceInterface.IAgencyService;

@Service
public class AgencyService implements IAgencyService {

	@Autowired
	private AgencyRepo agencyRepo;

	@Autowired
	private AgencyOfficeRepo officeRepo;

	// AGENCY

	public List<AgencyResponseDto> getAllAgencies() {
		List<Agency> agencyList =agencyRepo.findAll();
		return agencyList.stream().map(AgencyMapper::toDTO).toList();
	}

	public AgencyResponseDto getAgencyById(Long id) {
		Agency agency = agencyRepo.findById(id).orElseThrow(() -> new RuntimeException("Agency not found"));
		return AgencyMapper.toDTO(agency);
	}

	public AgencyResponseDto addAgency(AgencyRequestDto dto) { // CHANGED
        Agency agency = AgencyMapper.toEntity(dto); // CHANGED
        return AgencyMapper.toDTO(agencyRepo.save(agency)); // CHANGED
    }

	public AgencyResponseDto updateAgency(Long id, AgencyRequestDto dto) { // CHANGED
        Agency existing = agencyRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Agency not found"));

        existing.setName(dto.getName());
        existing.setContactPersonName(dto.getContactPersonName());
        existing.setEmail(dto.getEmail());
        existing.setPhone(dto.getPhone());

        return AgencyMapper.toDTO(agencyRepo.save(existing)); // CHANGED
    }

	public void deleteAgency(Long id) {
		agencyRepo.deleteById(id);
	}

	public AgencyResponseDto getAgencyByName(String name) {
		Agency agency = agencyRepo.findByNameIgnoreCase(name)
				.orElseThrow(() -> new RuntimeException("Agency not found"));
		return AgencyMapper.toDTO(agency);
	}

	public boolean agencyExists(String name) {
		return agencyRepo.existsByNameIgnoreCase(name);
	}

	public AgencyResponseDto getAgencyByEmail(String email) {
		Agency agency = agencyRepo.findByEmailIgnoreCase(email)
				.orElseThrow(() -> new RuntimeException("Agency not found"));

		return AgencyMapper.toDTO(agency);
	}

	public long countAgencies() {
		return agencyRepo.count();
	}

	public AgencyResponseDto updateAgencyPartial(Long id, AgencyRequestDto dto) {
		Agency agency = agencyRepo.findById(id).orElseThrow(() -> new RuntimeException("Agency not found"));

		if (dto.getName() != null)
			agency.setName(dto.getName());
		if (dto.getEmail() != null)
			agency.setEmail(dto.getEmail());
		if (dto.getPhone() != null)
			agency.setPhone(dto.getPhone());
		if (dto.getContactPersonName() != null)
			agency.setContactPersonName(dto.getContactPersonName());

		return AgencyMapper.toDTO(agencyRepo.save(agency));
	}

	// AGENCY OFFICE

	public List<AgencyOfficeResponseDto> getAllOffices() { // CHANGED
        return officeRepo.findAll().stream()
                .map(AgencyOfficeMapper::toDTO)
                .toList();
    }

	public AgencyOfficeResponseDto getOfficeById(Long id) { // CHANGED
        AgencyOffice office = officeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found"));
        return AgencyOfficeMapper.toDTO(office);
    }

	public List<AgencyOfficeResponseDto> getOfficesByAgency(Long agencyId) {
		return officeRepo.findByAgency_AgencyId(agencyId).stream().map(AgencyOfficeMapper::toDTO).toList();
	}

	public AgencyOfficeResponseDto addOffice(AgencyOfficeRequestDto dto) {

		Agency agency = agencyRepo.findById(dto.getAgencyId())
				.orElseThrow(() -> new RuntimeException("Agency not found"));

		AgencyOffice office = new AgencyOffice();
		office.setOfficeMail(dto.getOfficeMail());
		office.setOfficeContactPersonName(dto.getOfficeContactPersonName());
		office.setOfficeContactNumber(dto.getOfficeContactNumber());
		office.setAgency(agency);

		AgencyOffice saved = officeRepo.save(office);

		return AgencyOfficeMapper.toDTO(saved);
	}

	public AgencyOfficeResponseDto updateOffice(Long id, AgencyOfficeRequestDto dto) { // CHANGED
        AgencyOffice existing = officeRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Office not found"));

        existing.setOfficeMail(dto.getOfficeMail());
        existing.setOfficeContactPersonName(dto.getOfficeContactPersonName());
        existing.setOfficeContactNumber(dto.getOfficeContactNumber());

        if (dto.getAgencyId() != null) { // CHANGED
            Agency agency = agencyRepo.findById(dto.getAgencyId())
                    .orElseThrow(() -> new RuntimeException("Agency not found"));
            existing.setAgency(agency);
        }

        return AgencyOfficeMapper.toDTO(officeRepo.save(existing)); // CHANGED
    }

	public void deleteOffice(Long id) {
		officeRepo.deleteById(id);
	}

	public long countOfficesByAgency(Long agencyId) {
		return officeRepo.countByAgency_AgencyId(agencyId);
	}

	public AgencyOfficeResponseDto getOfficeByEmail(String email) {
		AgencyOffice office = officeRepo.findByOfficeMailIgnoreCase(email)
				.orElseThrow(() -> new RuntimeException("Office not found"));

		return AgencyOfficeMapper.toDTO(office);
	}

	public List<AgencyOfficeResponseDto> getByContactPerson(String name) {
		return officeRepo.findByOfficeContactPersonNameIgnoreCase(name).stream().map(AgencyOfficeMapper::toDTO)
				.toList();
	}

	public List<AgencyOfficeResponseDto> addOffices(List<AgencyOfficeRequestDto> dtos) {

		return dtos.stream().map(dto -> {
			Agency agency = agencyRepo.findById(dto.getAgencyId())
					.orElseThrow(() -> new RuntimeException("Agency not found"));

			AgencyOffice office = new AgencyOffice();
			office.setOfficeMail(dto.getOfficeMail());
			office.setOfficeContactPersonName(dto.getOfficeContactPersonName());
			office.setOfficeContactNumber(dto.getOfficeContactNumber());
			office.setAgency(agency);

			return AgencyOfficeMapper.toDTO(officeRepo.save(office));
		}).toList();
	}

	public void deleteOfficesByAgency(Long agencyId) {
		officeRepo.deleteByAgency_AgencyId(agencyId);
	}
}