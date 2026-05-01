package com.busbooking.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.busbooking.controller.AdminAgencyController;
import com.busbooking.dto.AgencyOfficeRequestDto;
import com.busbooking.dto.AgencyOfficeResponseDto;
import com.busbooking.dto.AgencyRequestDto;
import com.busbooking.dto.AgencyResponseDto;
import com.busbooking.exception.DuplicateResourceException;
import com.busbooking.exception.ResourceNotFoundException;
import com.busbooking.security.JwtService;
import com.busbooking.service.AgencyService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AdminAgencyController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypass security filters for isolated controller testing
class AdminAgencyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AgencyService agencyService;

    // Security dependencies required for context load
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private AgencyResponseDto agencyDto1, agencyDto2;
    private AgencyOfficeResponseDto officeDto1, officeDto2;
    private AgencyRequestDto agencyRequest;
    private AgencyOfficeRequestDto officeRequest;

    @BeforeEach
    void setUp() {
        // Agencies
        agencyDto1 = new AgencyResponseDto();
        agencyDto1.setAgencyId(1L);
        agencyDto1.setName("TravelCorp");
        agencyDto1.setContactPersonName("Alice");
        agencyDto1.setEmail("alice@travel.com");
        agencyDto1.setPhone("1111111111");

        agencyDto2 = new AgencyResponseDto();
        agencyDto2.setAgencyId(2L);
        agencyDto2.setName("HolidayInc");
        agencyDto2.setContactPersonName("Bob");
        agencyDto2.setEmail("bob@holiday.com");
        agencyDto2.setPhone("2222222222");

        agencyRequest = new AgencyRequestDto();
        agencyRequest.setName("NewAgency");
        agencyRequest.setContactPersonName("Charlie");
        agencyRequest.setEmail("charlie@new.com");
        agencyRequest.setPhone("3333333333");

        // Offices
        officeDto1 = new AgencyOfficeResponseDto();
        officeDto1.setOfficeId(10L);
        officeDto1.setOfficeMail("office1@travel.com");
        officeDto1.setOfficeContactPersonName("Manager1");
        officeDto1.setOfficeContactNumber("4444444444");
        officeDto1.setAgencyId(1L);

        officeDto2 = new AgencyOfficeResponseDto();
        officeDto2.setOfficeId(11L);
        officeDto2.setOfficeMail("office2@holiday.com");
        officeDto2.setOfficeContactPersonName("Manager2");
        officeDto2.setOfficeContactNumber("5555555555");
        officeDto2.setAgencyId(2L);

        officeRequest = new AgencyOfficeRequestDto();
        officeRequest.setAgencyId(1L);
        officeRequest.setOfficeMail("new@office.com");
        officeRequest.setOfficeContactPersonName("NewMgr");
        officeRequest.setOfficeContactNumber("6666666666");
    }

    // ===================== AGENCY ENDPOINTS =====================

    @Test
    void addAgency_ShouldCreateAndReturn() throws Exception {
        when(agencyService.addAgency(any(AgencyRequestDto.class))).thenReturn(agencyDto1);

        mockMvc.perform(post("/admin/agencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agencyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TravelCorp"))
                .andExpect(jsonPath("$.email").value("alice@travel.com"));
    }

    @Test
    void addAgency_ShouldReturnConflict_WhenDuplicate() throws Exception {
        when(agencyService.addAgency(any(AgencyRequestDto.class)))
                .thenThrow(new DuplicateResourceException("Agency exists"));

        mockMvc.perform(post("/admin/agencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agencyRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void addAgency_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
        mockMvc.perform(post("/admin/agencies")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAgency_ShouldUpdateAndReturn() throws Exception {
        when(agencyService.updateAgency(eq(1L), any(AgencyRequestDto.class))).thenReturn(agencyDto2);

        mockMvc.perform(put("/admin/agencies/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agencyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("HolidayInc"))
                .andExpect(jsonPath("$.phone").value("2222222222"));
    }

    @Test
    void updateAgency_ShouldReturnNotFound_WhenMissing() throws Exception {
        when(agencyService.updateAgency(eq(99L), any(AgencyRequestDto.class)))
                .thenThrow(new ResourceNotFoundException("Agency not found"));

        mockMvc.perform(put("/admin/agencies/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agencyRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateAgency_ShouldReturnBadRequest_WhenInvalidBody() throws Exception {
        mockMvc.perform(put("/admin/agencies/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateAgencyPartial_ShouldApplyChanges() throws Exception {
        when(agencyService.updateAgencyPartial(eq(1L), any(AgencyRequestDto.class))).thenReturn(agencyDto1);

        mockMvc.perform(patch("/admin/agencies/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agencyRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TravelCorp"));
    }

    @Test
    void updateAgencyPartial_ShouldReturnNotFound() throws Exception {
        when(agencyService.updateAgencyPartial(eq(99L), any(AgencyRequestDto.class)))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(patch("/admin/agencies/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(agencyRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateAgencyPartial_ShouldReturnBadRequest_WhenEmptyBody() throws Exception {
        mockMvc.perform(patch("/admin/agencies/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteAgency_ShouldReturnOkMessage() throws Exception {
        doNothing().when(agencyService).deleteAgency(1L);

        mockMvc.perform(delete("/admin/agencies/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("Agency deleted successfully"));
        verify(agencyService).deleteAgency(1L);
    }

    @Test
    void deleteAgency_ShouldReturnNotFound_WhenMissing() throws Exception {
        doThrow(new ResourceNotFoundException("Not found")).when(agencyService).deleteAgency(99L);

        mockMvc.perform(delete("/admin/agencies/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteAgency_ShouldSucceedOnValidId() throws Exception {
        doNothing().when(agencyService).deleteAgency(1L);

        mockMvc.perform(delete("/admin/agencies/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getAgencyById_ShouldReturnAgency() throws Exception {
        when(agencyService.getAgencyById(1L)).thenReturn(agencyDto1);

        mockMvc.perform(get("/admin/agencies/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TravelCorp"))
                .andExpect(jsonPath("$.email").value("alice@travel.com"));
    }

    @Test
    void getAgencyById_ShouldReturnNotFound() throws Exception {
        when(agencyService.getAgencyById(99L))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/admin/agencies/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAgencyById_ShouldReturnPhone() throws Exception {
        when(agencyService.getAgencyById(2L)).thenReturn(agencyDto2);

        mockMvc.perform(get("/admin/agencies/{id}", 2L))
                .andExpect(jsonPath("$.phone").value("2222222222"));
    }

    @Test
    void getAllAgencies_ShouldReturnList() throws Exception {
        when(agencyService.getAllAgencies()).thenReturn(Arrays.asList(agencyDto1, agencyDto2));

        mockMvc.perform(get("/admin/agencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("TravelCorp"));
    }

    @Test
    void getAllAgencies_ShouldReturnEmpty() throws Exception {
        when(agencyService.getAllAgencies()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/agencies"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getAgencyByName_ShouldReturnFound() throws Exception {
        when(agencyService.getAgencyByName("TravelCorp")).thenReturn(agencyDto1);

        mockMvc.perform(get("/admin/agencies/search").param("name", "TravelCorp"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("TravelCorp"));
    }

    @Test
    void getAgencyByName_ShouldReturnNotFound() throws Exception {
        when(agencyService.getAgencyByName("Unknown"))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/admin/agencies/search").param("name", "Unknown"))
                .andExpect(status().isNotFound());
    }

    @Test
    void agencyExists_ShouldReturnTrue() throws Exception {
        when(agencyService.agencyExists("TravelCorp")).thenReturn(true);
        mockMvc.perform(get("/admin/agencies/exists").param("name", "TravelCorp"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void agencyExists_ShouldReturnFalse() throws Exception {
        when(agencyService.agencyExists("NoCorp")).thenReturn(false);
        mockMvc.perform(get("/admin/agencies/exists").param("name", "NoCorp"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void getAgencyByEmail_ShouldReturnAgency() throws Exception {
        when(agencyService.getAgencyByEmail("alice@travel.com")).thenReturn(agencyDto1);

        mockMvc.perform(get("/admin/agencies/email/{email}", "alice@travel.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("alice@travel.com"));
    }

    @Test
    void getAgencyByEmail_ShouldReturnNotFound() throws Exception {
        when(agencyService.getAgencyByEmail("missing@mail.com"))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/admin/agencies/email/{email}", "missing@mail.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void countAgencies_ShouldReturnNumber() throws Exception {
        when(agencyService.countAgencies()).thenReturn(5L);
        mockMvc.perform(get("/admin/agencies/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("5"));
    }

    @Test
    void countAgencies_ShouldReturnZero() throws Exception {
        when(agencyService.countAgencies()).thenReturn(0L);
        mockMvc.perform(get("/admin/agencies/count"))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    // ===================== AGENCY OFFICE ENDPOINTS =====================

    @Test
    void addOffice_ShouldCreateAndReturn() throws Exception {
        when(agencyService.addOffice(any(AgencyOfficeRequestDto.class))).thenReturn(officeDto1);

        mockMvc.perform(post("/admin/agency-offices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(officeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officeMail").value("office1@travel.com"))
                .andExpect(jsonPath("$.agencyId").value(1));
    }

    @Test
    void addOffice_ShouldReturnConflict_WhenDuplicate() throws Exception {
        when(agencyService.addOffice(any(AgencyOfficeRequestDto.class)))
                .thenThrow(new DuplicateResourceException("Office exists"));

        mockMvc.perform(post("/admin/agency-offices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(officeRequest)))
                .andExpect(status().isConflict());
    }

    @Test
    void addOffice_ShouldReturnBadRequest_WhenInvalid() throws Exception {
        mockMvc.perform(post("/admin/agency-offices")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateOffice_ShouldUpdateAndReturn() throws Exception {
        when(agencyService.updateOffice(eq(10L), any(AgencyOfficeRequestDto.class))).thenReturn(officeDto2);

        mockMvc.perform(put("/admin/agency-offices/{id}", 10L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(officeRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officeMail").value("office2@holiday.com"));
    }

    @Test
    void updateOffice_ShouldReturnNotFound() throws Exception {
        when(agencyService.updateOffice(eq(99L), any(AgencyOfficeRequestDto.class)))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(put("/admin/agency-offices/{id}", 99L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(officeRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateOffice_ShouldReturnBadRequest_WhenInvalidBody() throws Exception {
        mockMvc.perform(put("/admin/agency-offices/{id}", 10L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteOffice_ShouldReturnOk() throws Exception {
        doNothing().when(agencyService).deleteOffice(10L);

        mockMvc.perform(delete("/admin/agency-offices/{id}", 10L))
                .andExpect(status().isOk())
                .andExpect(content().string("Office deleted successfully"));
        verify(agencyService).deleteOffice(10L);
    }

    @Test
    void deleteOffice_ShouldReturnNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Not found")).when(agencyService).deleteOffice(99L);

        mockMvc.perform(delete("/admin/agency-offices/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteOffice_ShouldSucceedOnValidId() throws Exception {
        doNothing().when(agencyService).deleteOffice(10L);
        mockMvc.perform(delete("/admin/agency-offices/{id}", 10L))
                .andExpect(status().isOk());
    }

    @Test
    void getOfficeById_ShouldReturnOffice() throws Exception {
        when(agencyService.getOfficeById(10L)).thenReturn(officeDto1);

        mockMvc.perform(get("/admin/agency-offices/{id}", 10L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officeMail").value("office1@travel.com"))
                .andExpect(jsonPath("$.officeContactPersonName").value("Manager1"));
    }

    @Test
    void getOfficeById_ShouldReturnNotFound() throws Exception {
        when(agencyService.getOfficeById(99L))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/admin/agency-offices/{id}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getOfficeById_ShouldReturnAgencyId() throws Exception {
        when(agencyService.getOfficeById(11L)).thenReturn(officeDto2);

        mockMvc.perform(get("/admin/agency-offices/{id}", 11L))
                .andExpect(jsonPath("$.agencyId").value(2));
    }

    @Test
    void addOffices_ShouldBulkCreateAndReturnList() throws Exception {
        List<AgencyOfficeRequestDto> requestList = Arrays.asList(officeRequest, officeRequest);
        List<AgencyOfficeResponseDto> responseList = Arrays.asList(officeDto1, officeDto2);
        when(agencyService.addOffices(anyList())).thenReturn(responseList);

        mockMvc.perform(post("/admin/agency-offices/bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestList)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].officeMail").value("office1@travel.com"))
                .andExpect(jsonPath("$[1].officeMail").value("office2@holiday.com"));
    }

    @Test
    void addOffices_ShouldThrowWhenServiceFails() throws Exception {
        when(agencyService.addOffices(anyList()))
                .thenThrow(new ResourceNotFoundException("Agency not found"));

        mockMvc.perform(post("/admin/agency-offices/bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Collections.singletonList(officeRequest))))
                .andExpect(status().isNotFound());
    }

    @Test
    void addOffices_ShouldReturnBadRequest_WhenEmptyList() throws Exception {
        mockMvc.perform(post("/admin/agency-offices/bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[]"))
                .andExpect(status().isOk()); 
    }

    @Test
    void deleteOfficesByAgency_ShouldReturnString() throws Exception {
        doNothing().when(agencyService).deleteOfficesByAgency(1L);

        mockMvc.perform(delete("/admin/agency-offices/agency/{agencyId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("All offices deleted"));
        verify(agencyService).deleteOfficesByAgency(1L);
    }

    @Test
    void deleteOfficesByAgency_ShouldThrowWhenServiceFails() throws Exception {
        doThrow(new ResourceNotFoundException("Agency not found")).when(agencyService).deleteOfficesByAgency(99L);

        mockMvc.perform(delete("/admin/agency-offices/agency/{agencyId}", 99L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteOfficesByAgency_ShouldSucceed() throws Exception {
        doNothing().when(agencyService).deleteOfficesByAgency(1L);
        mockMvc.perform(delete("/admin/agency-offices/agency/{agencyId}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void getAllOffices_ShouldReturnList() throws Exception {
        when(agencyService.getAllOffices()).thenReturn(Arrays.asList(officeDto1, officeDto2));

        mockMvc.perform(get("/admin/agency-offices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].officeMail").value("office1@travel.com"));
    }

    @Test
    void getAllOffices_ShouldReturnEmpty() throws Exception {
        when(agencyService.getAllOffices()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/agency-offices"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getOfficesByAgency_ShouldReturnFiltered() throws Exception {
        when(agencyService.getOfficesByAgency(1L)).thenReturn(Collections.singletonList(officeDto1));

        mockMvc.perform(get("/admin/agency-offices/agency/{agencyId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].agencyId").value(1));
    }

    @Test
    void getOfficesByAgency_ShouldReturnEmpty() throws Exception {
        when(agencyService.getOfficesByAgency(5L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/agency-offices/agency/{agencyId}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void countOfficesByAgency_ShouldReturnNumber() throws Exception {
        when(agencyService.countOfficesByAgency(1L)).thenReturn(3L);
        mockMvc.perform(get("/admin/agency-offices/count/{agencyId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().string("3"));
    }

    @Test
    void countOfficesByAgency_ShouldReturnZero() throws Exception {
        when(agencyService.countOfficesByAgency(5L)).thenReturn(0L);
        mockMvc.perform(get("/admin/agency-offices/count/{agencyId}", 5L))
                .andExpect(status().isOk())
                .andExpect(content().string("0"));
    }

    @Test
    void getOfficeByEmail_ShouldReturnOffice() throws Exception {
        when(agencyService.getOfficeByEmail("office1@travel.com")).thenReturn(officeDto1);

        mockMvc.perform(get("/admin/agency-offices/email/{email}", "office1@travel.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officeMail").value("office1@travel.com"));
    }

    @Test
    void getOfficeByEmail_ShouldReturnNotFound() throws Exception {
        when(agencyService.getOfficeByEmail("missing@mail.com"))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/admin/agency-offices/email/{email}", "missing@mail.com"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getByContactPerson_ShouldReturnList() throws Exception {
        when(agencyService.getByContactPerson("Manager1")).thenReturn(Collections.singletonList(officeDto1));

        mockMvc.perform(get("/admin/agency-offices/contact").param("name", "Manager1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].officeContactPersonName").value("Manager1"));
    }

    @Test
    void getByContactPerson_ShouldReturnEmpty() throws Exception {
        when(agencyService.getByContactPerson("Nobody")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/agency-offices/contact").param("name", "Nobody"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}