package com.busbooking.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import com.busbooking.controller.AdminBusDriverController;
import com.busbooking.dto.BusDto;
import com.busbooking.dto.DriverDto;
import com.busbooking.exception.DuplicateResourceException;
import com.busbooking.exception.ResourceNotFoundException;
import com.busbooking.security.JwtService;
import com.busbooking.service.BusDriverService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AdminBusDriverController.class)
@AutoConfigureMockMvc(addFilters = false) // Bypass security filters for isolated controller testing
class AdminBusDriverControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BusDriverService busDriverService;

    // Security dependencies required for context load
    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Autowired
    private ObjectMapper objectMapper;

    private BusDto busDto1, busDto2;
    private DriverDto driverDto1, driverDto2;

    @BeforeEach
    void setUp() {
        busDto1 = new BusDto();
        busDto1.setBusId(100L);
        busDto1.setRegistrationNumber("ABC123");
        busDto1.setType("Sleeper");
        busDto1.setCapacity(40);
        busDto1.setOfficeId(1L);

        busDto2 = new BusDto();
        busDto2.setBusId(101L);
        busDto2.setRegistrationNumber("XYZ789");
        busDto2.setType("Seater");
        busDto2.setCapacity(50);
        busDto2.setOfficeId(1L);

        driverDto1 = new DriverDto();
        driverDto1.setDriverId(200L);
        driverDto1.setLicenseNumber("LIC1001");
        driverDto1.setName("John Doe");
        driverDto1.setPhone("1234567890");
        driverDto1.setOfficeId(1L);
        driverDto1.setAddressId(10L);

        driverDto2 = new DriverDto();
        driverDto2.setDriverId(201L);
        driverDto2.setLicenseNumber("LIC1002");
        driverDto2.setName("Jane Smith");
        driverDto2.setPhone("0987654321");
        driverDto2.setOfficeId(2L);
        driverDto2.setAddressId(11L);
    }

    // ===================== BUS TESTS (3 important / 2 rest) =====================

    // ---------- Important: create, update, delete, getById ----------

    @Test
    void addBus_ShouldReturnCreatedBus() throws Exception {
        when(busDriverService.addBus(any(BusDto.class))).thenReturn(busDto1);

        mockMvc.perform(post("/admin/buses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(busDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationNumber").value("ABC123"))
                .andExpect(jsonPath("$.type").value("Sleeper"));

        verify(busDriverService).addBus(any(BusDto.class));
    }

    @Test
    void addBus_ShouldReturnConflict_WhenDuplicate() throws Exception {
        when(busDriverService.addBus(any(BusDto.class)))
                .thenThrow(new DuplicateResourceException("Duplicate"));

        mockMvc.perform(post("/admin/buses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(busDto1)))
                .andExpect(status().isConflict());
    }

    @Test
    void addBus_ShouldReturnBadRequest_WhenInvalidInput() throws Exception {
        mockMvc.perform(post("/admin/buses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateBus_ShouldReturnUpdatedBus() throws Exception {
        BusDto updated = new BusDto();
        updated.setRegistrationNumber("UPDATED");
        updated.setType("Sleeper");
        updated.setCapacity(60);
        updated.setOfficeId(1L);

        when(busDriverService.updateBus(eq(100L), any(BusDto.class))).thenReturn(updated);

        mockMvc.perform(put("/admin/buses/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationNumber").value("UPDATED"))
                .andExpect(jsonPath("$.capacity").value(60));
    }

    @Test
    void updateBus_ShouldReturnNotFound_WhenIdDoesNotExist() throws Exception {
        when(busDriverService.updateBus(eq(999L), any(BusDto.class)))
                .thenThrow(new ResourceNotFoundException("Bus not found"));

        mockMvc.perform(put("/admin/buses/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(busDto1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateBus_ShouldReturnBadRequest_WhenInvalidBody() throws Exception {
        mockMvc.perform(put("/admin/buses/{id}", 100L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))  // missing required fields
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteBus_ShouldReturnOkMessage() throws Exception {
        doNothing().when(busDriverService).deleteBus(100L);

        mockMvc.perform(delete("/admin/buses/{id}", 100L))
                .andExpect(status().isOk())
                .andExpect(content().string("Bus deleted successfully"));

        verify(busDriverService).deleteBus(100L);
    }

    @Test
    void deleteBus_ShouldReturnNotFound_WhenIdMissing() throws Exception {
        doThrow(new ResourceNotFoundException("Bus not found"))
                .when(busDriverService).deleteBus(999L);

        mockMvc.perform(delete("/admin/buses/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBus_ShouldStillReturnOk_WhenServiceSucceeds() throws Exception {
        // verify that the correct response is returned on successful delete
        doNothing().when(busDriverService).deleteBus(100L);
        mockMvc.perform(delete("/admin/buses/{id}", 100L))
                .andExpect(status().isOk());
    }

    @Test
    void getBusById_ShouldReturnBus() throws Exception {
        when(busDriverService.getBusById(100L)).thenReturn(busDto1);

        mockMvc.perform(get("/admin/buses/{id}", 100L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationNumber").value("ABC123"));
    }

    @Test
    void getBusById_ShouldReturnNotFound_WhenMissing() throws Exception {
        when(busDriverService.getBusById(999L))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/admin/buses/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBusById_ShouldReturnCorrectOfficeId() throws Exception {
        when(busDriverService.getBusById(100L)).thenReturn(busDto1);
        mockMvc.perform(get("/admin/buses/{id}", 100L))
                .andExpect(jsonPath("$.officeId").value(1));
    }

    // ---------- Rest of Bus endpoints (2 tests each) ----------

    @Test
    void getAllBuses_ShouldReturnList() throws Exception {
        when(busDriverService.getAllBuses()).thenReturn(Arrays.asList(busDto1, busDto2));

        mockMvc.perform(get("/admin/buses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].registrationNumber").value("ABC123"));
    }

    @Test
    void getAllBuses_ShouldReturnEmptyList() throws Exception {
        when(busDriverService.getAllBuses()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/buses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getBusesByOffice_ShouldReturnFiltered() throws Exception {
        when(busDriverService.getBusesByOffice(1L)).thenReturn(Collections.singletonList(busDto1));

        mockMvc.perform(get("/admin/buses/office/{officeId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].officeId").value(1));
    }

    @Test
    void getBusesByOffice_ShouldReturnEmpty() throws Exception {
        when(busDriverService.getBusesByOffice(5L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/buses/office/{officeId}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getBusesByType_ShouldReturnMatching() throws Exception {
        when(busDriverService.getBusesByType("Sleeper")).thenReturn(Collections.singletonList(busDto1));

        mockMvc.perform(get("/admin/buses/type/{type}", "Sleeper"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].type").value("Sleeper"));
    }

    @Test
    void getBusesByType_ShouldReturnEmpty() throws Exception {
        when(busDriverService.getBusesByType("Volvo")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/buses/type/{type}", "Volvo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getBusesByCapacity_ShouldReturnQualifying() throws Exception {
        when(busDriverService.getBusesByCapacity(40)).thenReturn(Arrays.asList(busDto1, busDto2));

        mockMvc.perform(get("/admin/buses/capacity/{capacity}", 40))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getBusesByCapacity_ShouldReturnEmpty() throws Exception {
        when(busDriverService.getBusesByCapacity(100)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/buses/capacity/{capacity}", 100))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getBusByRegistrationNumber_ShouldReturnBus() throws Exception {
        when(busDriverService.getBusByRegistrationNumber("ABC123")).thenReturn(busDto1);

        mockMvc.perform(get("/admin/buses/registration/{reg}", "ABC123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationNumber").value("ABC123"));
    }

    @Test
    void getBusByRegistrationNumber_ShouldReturnNotFound() throws Exception {
        when(busDriverService.getBusByRegistrationNumber("INVALID"))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/admin/buses/registration/{reg}", "INVALID"))
                .andExpect(status().isNotFound());
    }

    // ===================== DRIVER TESTS (3 important / 2 rest) =====================

    // ---------- Important: add, update, delete, getById ----------

    @Test
    void addDriver_ShouldReturnCreatedDriver() throws Exception {
        when(busDriverService.addDriver(any(DriverDto.class))).thenReturn(driverDto1);

        mockMvc.perform(post("/admin/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverDto1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseNumber").value("LIC1001"))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void addDriver_ShouldReturnConflict_WhenDuplicateLicense() throws Exception {
        when(busDriverService.addDriver(any(DriverDto.class)))
                .thenThrow(new DuplicateResourceException("Duplicate"));

        mockMvc.perform(post("/admin/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverDto1)))
                .andExpect(status().isConflict());
    }

    @Test
    void addDriver_ShouldReturnBadRequest_WhenInvalid() throws Exception {
        mockMvc.perform(post("/admin/drivers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateDriver_ShouldReturnUpdated() throws Exception {
        DriverDto updated = new DriverDto();
        updated.setLicenseNumber("LIC999");
        updated.setName("New Name");
        updated.setPhone("9999999999");

        when(busDriverService.updateDriver(eq(200L), any(DriverDto.class))).thenReturn(updated);

        mockMvc.perform(put("/admin/drivers/{id}", 200L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseNumber").value("LIC999"));
    }

    @Test
    void updateDriver_ShouldReturnNotFound() throws Exception {
        when(busDriverService.updateDriver(eq(999L), any(DriverDto.class)))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(put("/admin/drivers/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(driverDto1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateDriver_ShouldReturnBadRequest_WhenInvalid() throws Exception {
        mockMvc.perform(put("/admin/drivers/{id}", 200L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteDriver_ShouldReturnOk() throws Exception {
        doNothing().when(busDriverService).deleteDriver(200L);

        mockMvc.perform(delete("/admin/drivers/{id}", 200L))
                .andExpect(status().isOk())
                .andExpect(content().string("Driver deleted successfully"));

        verify(busDriverService).deleteDriver(200L);
    }

    @Test
    void deleteDriver_ShouldReturnNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Not found"))
                .when(busDriverService).deleteDriver(999L);

        mockMvc.perform(delete("/admin/drivers/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteDriver_ShouldSucceedAgain() throws Exception {
        doNothing().when(busDriverService).deleteDriver(200L);
        mockMvc.perform(delete("/admin/drivers/{id}", 200L))
                .andExpect(status().isOk());
    }

    @Test
    void getDriverById_ShouldReturnDriver() throws Exception {
        when(busDriverService.getDriverById(200L)).thenReturn(driverDto1);

        mockMvc.perform(get("/admin/drivers/{id}", 200L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getDriverById_ShouldReturnNotFound() throws Exception {
        when(busDriverService.getDriverById(999L))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/admin/drivers/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDriverById_ShouldReturnAddressId() throws Exception {
        when(busDriverService.getDriverById(200L)).thenReturn(driverDto1);
        mockMvc.perform(get("/admin/drivers/{id}", 200L))
                .andExpect(jsonPath("$.addressId").value(10));
    }

    // ---------- Rest of Driver endpoints (2 tests each) ----------

    @Test
    void getAllDrivers_ShouldReturnList() throws Exception {
        when(busDriverService.getAllDrivers()).thenReturn(Arrays.asList(driverDto1, driverDto2));

        mockMvc.perform(get("/admin/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getAllDrivers_ShouldReturnEmpty() throws Exception {
        when(busDriverService.getAllDrivers()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/drivers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getDriversByOffice_ShouldReturnFiltered() throws Exception {
        when(busDriverService.getDriversByOffice(1L)).thenReturn(Collections.singletonList(driverDto1));

        mockMvc.perform(get("/admin/drivers/office/{officeId}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].officeId").value(1));
    }

    @Test
    void getDriversByOffice_ShouldReturnEmpty() throws Exception {
        when(busDriverService.getDriversByOffice(5L)).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/drivers/office/{officeId}", 5L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getDriversByName_ShouldReturnMatching() throws Exception {
        when(busDriverService.getDriversByName("John")).thenReturn(Collections.singletonList(driverDto1));

        mockMvc.perform(get("/admin/drivers/name/{name}", "John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"));
    }

    @Test
    void getDriversByName_ShouldReturnEmpty() throws Exception {
        when(busDriverService.getDriversByName("Unknown")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/admin/drivers/name/{name}", "Unknown"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void getDriverByLicenseNumber_ShouldReturnDriver() throws Exception {
        when(busDriverService.getDriverByLicenseNumber("LIC1001")).thenReturn(driverDto1);

        mockMvc.perform(get("/admin/drivers/license/{lic}", "LIC1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.licenseNumber").value("LIC1001"));
    }

    @Test
    void getDriverByLicenseNumber_ShouldReturnNotFound() throws Exception {
        when(busDriverService.getDriverByLicenseNumber("INVALID"))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/admin/drivers/license/{lic}", "INVALID"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getDriverByPhone_ShouldReturnDriver() throws Exception {
        when(busDriverService.getDriverByPhone("1234567890")).thenReturn(driverDto1);

        mockMvc.perform(get("/admin/drivers/phone/{phone}", "1234567890"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("1234567890"));
    }

    @Test
    void getDriverByPhone_ShouldReturnNotFound() throws Exception {
        when(busDriverService.getDriverByPhone("0000"))
                .thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/admin/drivers/phone/{phone}", "0000"))
                .andExpect(status().isNotFound());
    }
}