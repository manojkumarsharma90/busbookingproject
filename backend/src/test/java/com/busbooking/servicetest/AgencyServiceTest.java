package com.busbooking.servicetest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.busbooking.dto.AgencyOfficeRequestDto;
import com.busbooking.dto.AgencyOfficeResponseDto;
import com.busbooking.dto.AgencyRequestDto;
import com.busbooking.dto.AgencyResponseDto;
import com.busbooking.entity.Agency;
import com.busbooking.entity.AgencyOffice;
import com.busbooking.repository.AgencyOfficeRepo;
import com.busbooking.repository.AgencyRepo;
import com.busbooking.service.AgencyService;

@ExtendWith(MockitoExtension.class)
class AgencyServiceTest {

    @Mock
    private AgencyRepo agencyRepo;
    @Mock
    private AgencyOfficeRepo officeRepo;

    @InjectMocks
    private AgencyService agencyService;

    private Agency agency1, agency2;
    private AgencyOffice office1, office2;

    @BeforeEach
    void setUp() {
        agency1 = new Agency();
        agency1.setAgencyId(1L);
        agency1.setName("TravelCorp");
        agency1.setContactPersonName("Alice");
        agency1.setEmail("alice@travel.com");
        agency1.setPhone("1111111111");

        agency2 = new Agency();
        agency2.setAgencyId(2L);
        agency2.setName("HolidayInc");
        agency2.setContactPersonName("Bob");
        agency2.setEmail("bob@holiday.com");
        agency2.setPhone("2222222222");

        office1 = new AgencyOffice();
        office1.setOfficeId(10L);
        office1.setOfficeMail("office1@travel.com");
        office1.setOfficeContactPersonName("Manager1");
        office1.setOfficeContactNumber("3333333333");
        office1.setAgency(agency1);

        office2 = new AgencyOffice();
        office2.setOfficeId(11L);
        office2.setOfficeMail("office2@holiday.com");
        office2.setOfficeContactPersonName("Manager2");
        office2.setOfficeContactNumber("4444444444");
        office2.setAgency(agency2);
    }

    // ---------- Important: 3 tests ----------

    @Test
    void addAgency_ShouldSaveAndReturnDto() {
        AgencyRequestDto input = new AgencyRequestDto();
        input.setName("NewAgency");
        input.setEmail("new@agency.com");

        when(agencyRepo.save(any(Agency.class))).thenAnswer(inv -> {
            Agency a = inv.getArgument(0);
            a.setAgencyId(100L);
            return a;
        });

        AgencyResponseDto result = agencyService.addAgency(input);
        assertEquals("NewAgency", result.getName());
        assertEquals(Long.valueOf(100L), result.getAgencyId());
    }

    @Test
    void addAgency_ShouldPersistCorrectFields() {
        AgencyRequestDto input = new AgencyRequestDto();
        input.setName("Test");
        input.setEmail("test@test.com");
        input.setPhone("123");
        when(agencyRepo.save(any(Agency.class))).thenReturn(agency1);
        agencyService.addAgency(input);
        verify(agencyRepo).save(argThat(a ->
                a.getName().equals("Test") &&
                a.getEmail().equals("test@test.com") &&
                a.getPhone().equals("123")));
    }

    @Test
    void addAgency_ShouldHandleMissingOptionalFields() {
        AgencyRequestDto input = new AgencyRequestDto();
        input.setName("OnlyName");
        when(agencyRepo.save(any(Agency.class))).thenReturn(agency1);
        AgencyResponseDto result = agencyService.addAgency(input);
        assertNotNull(result);
        verify(agencyRepo).save(any(Agency.class));
    }

    @Test
    void updateAgency_ShouldUpdateAllFields() {
        AgencyRequestDto update = new AgencyRequestDto();
        update.setName("X");
        update.setEmail("x@x.com");
        when(agencyRepo.findById(1L)).thenReturn(Optional.of(agency1));
        when(agencyRepo.save(any(Agency.class))).thenReturn(agency1);
        AgencyResponseDto result = agencyService.updateAgency(1L, update);
        assertEquals("X", result.getName());
        assertEquals("x@x.com", result.getEmail());
    }

    @Test
    void updateAgency_ShouldThrowException_WhenNotFound() {
        when(agencyRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.updateAgency(99L, new AgencyRequestDto()));
    }

    @Test
    void updateAgency_ShouldOnlyCallSaveOnce() {
        when(agencyRepo.findById(1L)).thenReturn(Optional.of(agency1));
        when(agencyRepo.save(any(Agency.class))).thenReturn(agency1);
        agencyService.updateAgency(1L, new AgencyRequestDto());
        verify(agencyRepo, times(1)).save(any(Agency.class));
    }

    @Test
    void getAgencyById_ShouldReturnCorrectAgency() {
        when(agencyRepo.findById(1L)).thenReturn(Optional.of(agency1));
        AgencyResponseDto result = agencyService.getAgencyById(1L);
        assertEquals("TravelCorp", result.getName());
        assertEquals("alice@travel.com", result.getEmail());
    }

    @Test
    void getAgencyById_ShouldThrowException_WhenNotFound() {
        when(agencyRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.getAgencyById(99L));
    }

    @Test
    void getAgencyById_ShouldReturnPhoneNumber() {
        when(agencyRepo.findById(2L)).thenReturn(Optional.of(agency2));
        AgencyResponseDto result = agencyService.getAgencyById(2L);
        assertEquals("2222222222", result.getPhone());
    }

    @Test
    void getAgencyByName_ShouldReturnWhenFound() {
        when(agencyRepo.findByNameIgnoreCase("TravelCorp")).thenReturn(Optional.of(agency1));
        AgencyResponseDto result = agencyService.getAgencyByName("TravelCorp");
        assertEquals("TravelCorp", result.getName());
    }

    @Test
    void getAgencyByName_ShouldThrowWhenNotFound() {
        when(agencyRepo.findByNameIgnoreCase("Missing")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.getAgencyByName("Missing"));
    }

    @Test
    void getAgencyByName_ShouldBeCaseInsensitive() {
        when(agencyRepo.findByNameIgnoreCase("TRAVELCORP")).thenReturn(Optional.of(agency1));
        AgencyResponseDto result = agencyService.getAgencyByName("TRAVELCORP");
        assertEquals("TravelCorp", result.getName());
    }

    @Test
    void getAgencyByEmail_ShouldReturnWhenFound() {
        when(agencyRepo.findByEmailIgnoreCase("alice@travel.com")).thenReturn(Optional.of(agency1));
        AgencyResponseDto result = agencyService.getAgencyByEmail("alice@travel.com");
        assertEquals("Alice", result.getContactPersonName());
    }

    @Test
    void getAgencyByEmail_ShouldThrowWhenNotFound() {
        when(agencyRepo.findByEmailIgnoreCase("no@mail.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.getAgencyByEmail("no@mail.com"));
    }

    @Test
    void getAgencyByEmail_ShouldBeCaseInsensitive() {
        when(agencyRepo.findByEmailIgnoreCase("ALICE@TRAVEL.COM")).thenReturn(Optional.of(agency1));
        AgencyResponseDto result = agencyService.getAgencyByEmail("ALICE@TRAVEL.COM");
        assertEquals("alice@travel.com", result.getEmail());
    }

    @Test
    void updateAgencyPartial_ShouldUpdateAllProvidedFields() {
        AgencyRequestDto patch = new AgencyRequestDto();
        patch.setName("Part");
        patch.setEmail("part@mail.com");

        when(agencyRepo.findById(1L)).thenReturn(Optional.of(agency1));
        when(agencyRepo.save(any(Agency.class))).thenReturn(agency1);

        AgencyResponseDto result = agencyService.updateAgencyPartial(1L, patch);
        assertEquals("Part", result.getName());
        assertEquals("part@mail.com", result.getEmail());
        assertEquals("1111111111", result.getPhone()); // unchanged
    }

    @Test
    void updateAgencyPartial_ShouldKeepOldValuesForNullFields() {
        AgencyRequestDto patch = new AgencyRequestDto();
        patch.setPhone("0000000000");

        when(agencyRepo.findById(2L)).thenReturn(Optional.of(agency2));
        when(agencyRepo.save(any(Agency.class))).thenReturn(agency2);

        AgencyResponseDto result = agencyService.updateAgencyPartial(2L, patch);
        assertEquals("HolidayInc", result.getName()); // unchanged
        assertEquals("0000000000", result.getPhone());
    }

    @Test
    void updateAgencyPartial_ShouldThrowWhenNotFound() {
        when(agencyRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () ->
                agencyService.updateAgencyPartial(99L, new AgencyRequestDto()));
    }

    @Test
    void addOffice_ShouldSaveAndReturnDto() {
        AgencyOfficeRequestDto input = new AgencyOfficeRequestDto();
        input.setAgencyId(1L);
        input.setOfficeMail("new@office.com");
        input.setOfficeContactPersonName("N");
        input.setOfficeContactNumber("555");

        when(agencyRepo.findById(1L)).thenReturn(Optional.of(agency1));
        when(officeRepo.save(any(AgencyOffice.class))).thenAnswer(inv -> {
            AgencyOffice o = inv.getArgument(0);
            o.setOfficeId(100L);
            return o;
        });

        AgencyOfficeResponseDto result = agencyService.addOffice(input);
        assertEquals("new@office.com", result.getOfficeMail());
        assertEquals(Long.valueOf(100L), result.getOfficeId());
    }

    @Test
    void addOffice_ShouldThrowWhenAgencyNotFound() {
        AgencyOfficeRequestDto input = new AgencyOfficeRequestDto();
        input.setAgencyId(99L);
        when(agencyRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.addOffice(input));
    }

    @Test
    void addOffice_ShouldSetCorrectAgencyOnEntity() {
        AgencyOfficeRequestDto input = new AgencyOfficeRequestDto();
        input.setAgencyId(2L);
        when(agencyRepo.findById(2L)).thenReturn(Optional.of(agency2));
        when(officeRepo.save(any(AgencyOffice.class))).thenReturn(office2);
        agencyService.addOffice(input);
        verify(officeRepo).save(argThat(o -> o.getAgency().getAgencyId().equals(2L)));
    }

    @Test
    void updateOffice_ShouldUpdateAllFieldsAndAgency() {
        AgencyOfficeRequestDto update = new AgencyOfficeRequestDto();
        update.setOfficeMail("upd@off.com");
        update.setAgencyId(2L);

        when(officeRepo.findById(10L)).thenReturn(Optional.of(office1));
        when(agencyRepo.findById(2L)).thenReturn(Optional.of(agency2));
        when(officeRepo.save(any(AgencyOffice.class))).thenReturn(office1);

        AgencyOfficeResponseDto result = agencyService.updateOffice(10L, update);
        assertEquals("upd@off.com", result.getOfficeMail());
        assertEquals(Long.valueOf(2L), result.getAgencyId());
    }

    @Test
    void updateOffice_ShouldThrowWhenOfficeNotFound() {
        when(officeRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () ->
                agencyService.updateOffice(99L, new AgencyOfficeRequestDto()));
    }

    @Test
    void updateOffice_ShouldThrowWhenAgencyNotFound() {
        AgencyOfficeRequestDto update = new AgencyOfficeRequestDto();
        update.setAgencyId(99L);
        when(officeRepo.findById(10L)).thenReturn(Optional.of(office1));
        when(agencyRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.updateOffice(10L, update));
    }

    @Test
    void getOfficesByAgency_ShouldReturnOfficeList() {
        when(officeRepo.findByAgency_AgencyId(1L)).thenReturn(Arrays.asList(office1));
        List<AgencyOfficeResponseDto> result = agencyService.getOfficesByAgency(1L);
        assertEquals(1, result.size());
        assertEquals("office1@travel.com", result.get(0).getOfficeMail());
    }

    @Test
    void getOfficesByAgency_ShouldReturnEmptyWhenNone() {
        when(officeRepo.findByAgency_AgencyId(5L)).thenReturn(Collections.emptyList());
        List<AgencyOfficeResponseDto> result = agencyService.getOfficesByAgency(5L);
        assertTrue(result.isEmpty());
    }

    @Test
    void getOfficesByAgency_ShouldMapFieldsCorrectly() {
        when(officeRepo.findByAgency_AgencyId(2L)).thenReturn(List.of(office2));
        List<AgencyOfficeResponseDto> result = agencyService.getOfficesByAgency(2L);
        AgencyOfficeResponseDto dto = result.get(0);
        assertEquals(office2.getOfficeId(), dto.getOfficeId());
        assertEquals(office2.getOfficeContactNumber(), dto.getOfficeContactNumber());
    }

    @Test
    void getOfficeByEmail_ShouldReturnWhenFound() {
        when(officeRepo.findByOfficeMailIgnoreCase("office1@travel.com")).thenReturn(Optional.of(office1));
        AgencyOfficeResponseDto result = agencyService.getOfficeByEmail("office1@travel.com");
        assertEquals("Manager1", result.getOfficeContactPersonName());
    }

    @Test
    void getOfficeByEmail_ShouldThrowWhenNotFound() {
        when(officeRepo.findByOfficeMailIgnoreCase("missing@mail.com")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.getOfficeByEmail("missing@mail.com"));
    }

    @Test
    void getOfficeByEmail_ShouldBeCaseInsensitive() {
        when(officeRepo.findByOfficeMailIgnoreCase("OFFICE1@TRAVEL.COM")).thenReturn(Optional.of(office1));
        AgencyOfficeResponseDto result = agencyService.getOfficeByEmail("OFFICE1@TRAVEL.COM");
        assertEquals("office1@travel.com", result.getOfficeMail());
    }

    @Test
    void getByContactPerson_ShouldReturnMatchingOffices() {
        when(officeRepo.findByOfficeContactPersonNameIgnoreCase("Manager1")).thenReturn(Arrays.asList(office1));
        List<AgencyOfficeResponseDto> result = agencyService.getByContactPerson("Manager1");
        assertEquals(1, result.size());
        assertEquals("Manager1", result.get(0).getOfficeContactPersonName());
    }

    @Test
    void getByContactPerson_ShouldReturnEmptyWhenNone() {
        when(officeRepo.findByOfficeContactPersonNameIgnoreCase("Nobody")).thenReturn(Collections.emptyList());
        List<AgencyOfficeResponseDto> result = agencyService.getByContactPerson("Nobody");
        assertTrue(result.isEmpty());
    }

    @Test
    void getByContactPerson_ShouldBeCaseInsensitiveAndMapMulti() {
        when(officeRepo.findByOfficeContactPersonNameIgnoreCase("manager2")).thenReturn(List.of(office2));
        List<AgencyOfficeResponseDto> result = agencyService.getByContactPerson("manager2");
        assertEquals(1, result.size());
        assertEquals("Manager2", result.get(0).getOfficeContactPersonName());
    }

    @Test
    void addOffices_ShouldAddMultipleAndReturnDtos() {
        AgencyOfficeRequestDto dto1 = new AgencyOfficeRequestDto();
        dto1.setAgencyId(1L);
        dto1.setOfficeMail("m1@mail.com");
        AgencyOfficeRequestDto dto2 = new AgencyOfficeRequestDto();
        dto2.setAgencyId(2L);
        dto2.setOfficeMail("m2@mail.com");

        when(agencyRepo.findById(1L)).thenReturn(Optional.of(agency1));
        when(agencyRepo.findById(2L)).thenReturn(Optional.of(agency2));
        when(officeRepo.save(any(AgencyOffice.class))).thenAnswer(inv -> {
            AgencyOffice o = inv.getArgument(0);
            o.setOfficeId(o.getAgency().getAgencyId() == 1L ? 10L : 11L);
            return o;
        });

        List<AgencyOfficeResponseDto> result = agencyService.addOffices(Arrays.asList(dto1, dto2));
        assertEquals(2, result.size());
        assertEquals("m1@mail.com", result.get(0).getOfficeMail());
        assertEquals("m2@mail.com", result.get(1).getOfficeMail());
    }

    @Test
    void addOffices_ShouldThrowWhenAnyAgencyNotFound() {
        AgencyOfficeRequestDto dto = new AgencyOfficeRequestDto();
        dto.setAgencyId(99L);
        when(agencyRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.addOffices(List.of(dto)));
    }

    @Test
    void addOffices_ShouldVerifyEachOfficeSaved() {
        AgencyOfficeRequestDto dto1 = new AgencyOfficeRequestDto();
        dto1.setAgencyId(1L);
        dto1.setOfficeMail("a@a.com");
        when(agencyRepo.findById(1L)).thenReturn(Optional.of(agency1));
        when(officeRepo.save(any(AgencyOffice.class))).thenReturn(office1);
        agencyService.addOffices(List.of(dto1));
        verify(officeRepo, times(1)).save(any(AgencyOffice.class));
    }

    @Test
    void deleteOfficesByAgency_ShouldCallRepositoryMethod() {
        doNothing().when(officeRepo).deleteByAgency_AgencyId(1L);
        agencyService.deleteOfficesByAgency(1L);
        verify(officeRepo).deleteByAgency_AgencyId(1L);
    }

    @Test
    void deleteOfficesByAgency_ShouldNotThrowWhenNoOffices() {
        doNothing().when(officeRepo).deleteByAgency_AgencyId(5L);
        assertDoesNotThrow(() -> agencyService.deleteOfficesByAgency(5L));
    }

    @Test
    void deleteOfficesByAgency_ShouldVerifySingleCall() {
        doNothing().when(officeRepo).deleteByAgency_AgencyId(1L);
        agencyService.deleteOfficesByAgency(1L);
        verify(officeRepo, times(1)).deleteByAgency_AgencyId(1L);
    }

    // ---------- Rest methods: 2 tests each ----------

    @Test
    void getAllAgencies_ShouldReturnList() {
        when(agencyRepo.findAll()).thenReturn(Arrays.asList(agency1, agency2));
        List<AgencyResponseDto> result = agencyService.getAllAgencies();
        assertEquals(2, result.size());
    }

    @Test
    void getAllAgencies_ShouldReturnEmpty() {
        when(agencyRepo.findAll()).thenReturn(Collections.emptyList());
        assertTrue(agencyService.getAllAgencies().isEmpty());
    }

    @Test
    void getAllOffices_ShouldReturnList() {
        when(officeRepo.findAll()).thenReturn(Arrays.asList(office1, office2));
        assertEquals(2, agencyService.getAllOffices().size());
    }

    @Test
    void getAllOffices_ShouldReturnEmpty() {
        when(officeRepo.findAll()).thenReturn(Collections.emptyList());
        assertTrue(agencyService.getAllOffices().isEmpty());
    }

    @Test
    void getOfficeById_ShouldReturnWhenFound() {
        when(officeRepo.findById(10L)).thenReturn(Optional.of(office1));
        AgencyOfficeResponseDto result = agencyService.getOfficeById(10L);
        assertEquals("office1@travel.com", result.getOfficeMail());
    }

    @Test
    void getOfficeById_ShouldThrowWhenNotFound() {
        when(officeRepo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> agencyService.getOfficeById(99L));
    }

    @Test
    void deleteAgency_ShouldCallRepository() {
        doNothing().when(agencyRepo).deleteById(1L);
        agencyService.deleteAgency(1L);
        verify(agencyRepo).deleteById(1L);
    }

    @Test
    void deleteAgency_ShouldNotThrow() {
        doNothing().when(agencyRepo).deleteById(99L);
        assertDoesNotThrow(() -> agencyService.deleteAgency(99L));
    }

    @Test
    void deleteOffice_ShouldCallRepository() {
        doNothing().when(officeRepo).deleteById(10L);
        agencyService.deleteOffice(10L);
        verify(officeRepo).deleteById(10L);
    }

    @Test
    void deleteOffice_ShouldNotThrow() {
        doNothing().when(officeRepo).deleteById(99L);
        assertDoesNotThrow(() -> agencyService.deleteOffice(99L));
    }

    @Test
    void countAgencies_ShouldReturnNumber() {
        when(agencyRepo.count()).thenReturn(5L);
        assertEquals(5L, agencyService.countAgencies());
    }

    @Test
    void countAgencies_ShouldReturnZero() {
        when(agencyRepo.count()).thenReturn(0L);
        assertEquals(0L, agencyService.countAgencies());
    }

    @Test
    void countOfficesByAgency_ShouldReturnCount() {
        when(officeRepo.countByAgency_AgencyId(1L)).thenReturn(3L);
        assertEquals(3L, agencyService.countOfficesByAgency(1L));
    }

    @Test
    void countOfficesByAgency_ShouldReturnZero() {
        when(officeRepo.countByAgency_AgencyId(5L)).thenReturn(0L);
        assertEquals(0L, agencyService.countOfficesByAgency(5L));
    }

    @Test
    void agencyExists_ShouldReturnTrue() {
        when(agencyRepo.existsByNameIgnoreCase("TravelCorp")).thenReturn(true);
        assertTrue(agencyService.agencyExists("TravelCorp"));
    }

    @Test
    void agencyExists_ShouldReturnFalse() {
        when(agencyRepo.existsByNameIgnoreCase("NoCorp")).thenReturn(false);
        assertFalse(agencyService.agencyExists("NoCorp"));
    }
}