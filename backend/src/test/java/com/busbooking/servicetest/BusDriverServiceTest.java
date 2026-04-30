package com.busbooking.servicetest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

import com.busbooking.dto.BusDto;
import com.busbooking.dto.DriverDto;
import com.busbooking.entity.Address;
import com.busbooking.entity.AgencyOffice;
import com.busbooking.entity.Bus;
import com.busbooking.entity.Driver;
import com.busbooking.exception.DuplicateResourceException;
import com.busbooking.exception.ResourceNotFoundException;
import com.busbooking.repository.AddressRepo;
import com.busbooking.repository.AgencyOfficeRepo;
import com.busbooking.repository.BusRepo;
import com.busbooking.repository.DriverRepo;
import com.busbooking.service.BusDriverService;

@ExtendWith(MockitoExtension.class)
class BusDriverServiceTest {

    @Mock
    private BusRepo busRepo;
    @Mock
    private DriverRepo driverRepo;
    @Mock
    private AgencyOfficeRepo officeRepo;
    @Mock
    private AddressRepo addressRepo;

    @InjectMocks
    private BusDriverService service;

    // Helper entities
    private Bus bus1, bus2;
    private Driver driver1, driver2;
    private AgencyOffice office;
    private Address address;

    @BeforeEach
    void setUp() {
        office = new AgencyOffice();
        office.setOfficeId(1L);
        office.setOfficeMail("main@agency.com");
        office.setOfficeContactPersonName("Main Office");

        address = new Address();
        address.setAddressId(10L);
        address.setCity("City");

        bus1 = new Bus();
        bus1.setBusId(100L);
        bus1.setRegistrationNumber("ABC123");
        bus1.setType("Sleeper");
        bus1.setCapacity(40);
        bus1.setOffice(office);

        bus2 = new Bus();
        bus2.setBusId(101L);
        bus2.setRegistrationNumber("XYZ789");
        bus2.setType("Seater");
        bus2.setCapacity(50);
        bus2.setOffice(office);

        driver1 = new Driver();
        driver1.setDriverId(200L);
        driver1.setLicenseNumber("LIC1001");
        driver1.setName("John Doe");
        driver1.setPhone("1234567890");
        driver1.setOffice(office);
        driver1.setAddress(address);

        driver2 = new Driver();
        driver2.setDriverId(201L);
        driver2.setLicenseNumber("LIC1002");
        driver2.setName("Jane Smith");
        driver2.setPhone("0987654321");
        driver2.setOffice(office);
        driver2.setAddress(address);
    }

    // ------------------- BUS APIs -------------------

    @Test
    void getAllBuses_ShouldReturnList() {
        when(busRepo.findAll()).thenReturn(Arrays.asList(bus1, bus2));

        List<BusDto> result = service.getAllBuses();

        assertEquals(2, result.size());
        assertEquals("ABC123", result.get(0).getRegistrationNumber());
        assertEquals("XYZ789", result.get(1).getRegistrationNumber());
        verify(busRepo).findAll();
    }

    @Test
    void getAllBuses_ShouldReturnEmptyList() {
        when(busRepo.findAll()).thenReturn(Collections.emptyList());

        List<BusDto> result = service.getAllBuses();

        assertTrue(result.isEmpty());
        verify(busRepo).findAll();
    }

    @Test
    void getAllBuses_ShouldReturnCorrectMapping() {
        when(busRepo.findAll()).thenReturn(List.of(bus1));

        List<BusDto> result = service.getAllBuses();

        assertEquals(1, result.size());
        BusDto dto = result.get(0);
        assertEquals(bus1.getBusId(), dto.getBusId());
        assertEquals(bus1.getRegistrationNumber(), dto.getRegistrationNumber());
        assertEquals(bus1.getType(), dto.getType());
        assertEquals(bus1.getCapacity(), dto.getCapacity());
        assertEquals(bus1.getOffice().getOfficeId(), dto.getOfficeId());
    }

    @Test
    void getBusById_ShouldReturnBus_WhenFound() {
        when(busRepo.findById(100L)).thenReturn(Optional.of(bus1));

        BusDto result = service.getBusById(100L);

        assertEquals("ABC123", result.getRegistrationNumber());
        verify(busRepo).findById(100L);
    }

    @Test
    void getBusById_ShouldThrowException_WhenNotFound() {
        when(busRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getBusById(999L));
        verify(busRepo).findById(999L);
    }

    @Test
    void getBusById_ShouldReturnCorrectOfficeId() {
        when(busRepo.findById(100L)).thenReturn(Optional.of(bus1));

        BusDto result = service.getBusById(100L);

        assertEquals(office.getOfficeId(), result.getOfficeId());
    }

    @Test
    void getBusesByOffice_ShouldReturnBuses() {
        when(busRepo.findByOffice_OfficeId(1L)).thenReturn(Arrays.asList(bus1, bus2));

        List<BusDto> result = service.getBusesByOffice(1L);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(b -> b.getOfficeId().equals(1L)));
    }

    @Test
    void getBusesByOffice_ShouldReturnEmpty_WhenNoBuses() {
        when(busRepo.findByOffice_OfficeId(5L)).thenReturn(Collections.emptyList());

        List<BusDto> result = service.getBusesByOffice(5L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getBusesByOffice_ShouldMapFieldsCorrectly() {
        when(busRepo.findByOffice_OfficeId(1L)).thenReturn(List.of(bus1));

        List<BusDto> result = service.getBusesByOffice(1L);
        BusDto dto = result.get(0);

        assertEquals(bus1.getBusId(), dto.getBusId());
        assertEquals(bus1.getRegistrationNumber(), dto.getRegistrationNumber());
    }

    @Test
    void getBusesByType_ShouldReturnFilteredList() {
        when(busRepo.findByTypeIgnoreCase("Sleeper")).thenReturn(List.of(bus1));

        List<BusDto> result = service.getBusesByType("Sleeper");

        assertEquals(1, result.size());
        assertEquals("Sleeper", result.get(0).getType());
    }

    @Test
    void getBusesByType_ShouldReturnEmpty_WhenNoMatch() {
        when(busRepo.findByTypeIgnoreCase("Volvo")).thenReturn(Collections.emptyList());

        List<BusDto> result = service.getBusesByType("Volvo");

        assertTrue(result.isEmpty());
    }

    @Test
    void getBusesByType_ShouldBeCaseInsensitive() {
        when(busRepo.findByTypeIgnoreCase("sleeper")).thenReturn(List.of(bus1));

        List<BusDto> result = service.getBusesByType("sleeper");

        assertEquals(1, result.size());
        assertEquals("Sleeper", result.get(0).getType());
    }

    @Test
    void getBusesByCapacity_ShouldReturnBusesWithCapacityGreaterOrEqual() {
        when(busRepo.findByCapacityGreaterThanEqual(40)).thenReturn(Arrays.asList(bus1, bus2));

        List<BusDto> result = service.getBusesByCapacity(40);

        assertEquals(2, result.size());
    }

    @Test
    void getBusesByCapacity_ShouldReturnEmpty_WhenNoneQualify() {
        when(busRepo.findByCapacityGreaterThanEqual(100)).thenReturn(Collections.emptyList());

        List<BusDto> result = service.getBusesByCapacity(100);

        assertTrue(result.isEmpty());
    }

    @Test
    void getBusesByCapacity_ShouldMapFirstResult() {
        when(busRepo.findByCapacityGreaterThanEqual(50)).thenReturn(List.of(bus2));

        List<BusDto> result = service.getBusesByCapacity(50);

        assertEquals(1, result.size());
        assertEquals("XYZ789", result.get(0).getRegistrationNumber());
    }

    @Test
    void getBusByRegistrationNumber_ShouldReturnBus_WhenFound() {
        when(busRepo.findByRegistrationNumber("ABC123")).thenReturn(Optional.of(bus1));

        BusDto result = service.getBusByRegistrationNumber("ABC123");

        assertEquals("ABC123", result.getRegistrationNumber());
    }

    @Test
    void getBusByRegistrationNumber_ShouldThrowException_WhenNotFound() {
        when(busRepo.findByRegistrationNumber("NOTFOUND")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getBusByRegistrationNumber("NOTFOUND"));
    }

    @Test
    void getBusByRegistrationNumber_ShouldReturnCorrectBusId() {
        when(busRepo.findByRegistrationNumber("ABC123")).thenReturn(Optional.of(bus1));

        BusDto result = service.getBusByRegistrationNumber("ABC123");

        assertEquals(bus1.getBusId(), result.getBusId());
    }

    @Test
    void addBus_ShouldSaveAndReturnDto() {
        BusDto inputDto = new BusDto();
        inputDto.setRegistrationNumber("NEW123");
        inputDto.setType("Sleeper");
        inputDto.setCapacity(30);
        inputDto.setOfficeId(1L);

        when(busRepo.existsByRegistrationNumber("NEW123")).thenReturn(false);
        when(officeRepo.findById(1L)).thenReturn(Optional.of(office));
        when(busRepo.save(any(Bus.class))).thenAnswer(inv -> {
            Bus b = inv.getArgument(0);
            b.setBusId(200L);
            return b;
        });

        BusDto result = service.addBus(inputDto);

        assertEquals("NEW123", result.getRegistrationNumber());
        assertEquals(1L, result.getOfficeId());
        verify(busRepo).save(any(Bus.class));
    }

    @Test
    void addBus_ShouldThrowDuplicateException_WhenRegNumberExists() {
        BusDto inputDto = new BusDto();
        inputDto.setRegistrationNumber("ABC123");

        when(busRepo.existsByRegistrationNumber("ABC123")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> service.addBus(inputDto));
        verify(busRepo, never()).save(any());
    }

    @Test
    void addBus_ShouldThrowException_WhenOfficeNotFound() {
        BusDto inputDto = new BusDto();
        inputDto.setRegistrationNumber("NEW123");
        inputDto.setOfficeId(99L);

        when(busRepo.existsByRegistrationNumber("NEW123")).thenReturn(false);
        when(officeRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.addBus(inputDto));
    }

    @Test
    void updateBus_ShouldUpdateExistingBus() {
        BusDto updateDto = new BusDto();
        updateDto.setRegistrationNumber("UPDATED");
        updateDto.setType("Seater");
        updateDto.setCapacity(60);
        updateDto.setOfficeId(1L);

        when(busRepo.findById(100L)).thenReturn(Optional.of(bus1));
        when(officeRepo.findById(1L)).thenReturn(Optional.of(office));
        when(busRepo.save(any(Bus.class))).thenReturn(bus1);

        BusDto result = service.updateBus(100L, updateDto);

        assertEquals("UPDATED", result.getRegistrationNumber());
        assertEquals("Seater", result.getType());
        assertEquals(60, result.getCapacity());
        verify(busRepo).save(bus1);
    }

    @Test
    void updateBus_ShouldThrowException_WhenBusNotFound() {
        when(busRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateBus(999L, new BusDto()));
    }

    @Test
    void updateBus_ShouldThrowException_WhenOfficeNotFound() {
        BusDto updateDto = new BusDto();
        updateDto.setOfficeId(99L);
        when(busRepo.findById(100L)).thenReturn(Optional.of(bus1));
        when(officeRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateBus(100L, updateDto));
    }

    @Test
    void deleteBus_ShouldDelete_WhenBusExists() {
        when(busRepo.findById(100L)).thenReturn(Optional.of(bus1));
        doNothing().when(busRepo).delete(bus1);

        assertDoesNotThrow(() -> service.deleteBus(100L));
        verify(busRepo).delete(bus1);
    }

    @Test
    void deleteBus_ShouldThrowException_WhenBusNotFound() {
        when(busRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteBus(999L));
    }

    @Test
    void deleteBus_ShouldOnlyCallDeleteOnce() {
        when(busRepo.findById(100L)).thenReturn(Optional.of(bus1));
        service.deleteBus(100L);
        verify(busRepo, times(1)).delete(bus1);
    }

    // ------------------- DRIVER APIs -------------------

    @Test
    void getAllDrivers_ShouldReturnList() {
        when(driverRepo.findAll()).thenReturn(Arrays.asList(driver1, driver2));

        List<DriverDto> result = service.getAllDrivers();

        assertEquals(2, result.size());
        verify(driverRepo).findAll();
    }

    @Test
    void getAllDrivers_ShouldReturnEmptyList() {
        when(driverRepo.findAll()).thenReturn(Collections.emptyList());

        List<DriverDto> result = service.getAllDrivers();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllDrivers_ShouldMapFieldsCorrectly() {
        when(driverRepo.findAll()).thenReturn(List.of(driver1));

        List<DriverDto> result = service.getAllDrivers();
        DriverDto dto = result.get(0);

        assertEquals(driver1.getDriverId(), dto.getDriverId());
        assertEquals(driver1.getLicenseNumber(), dto.getLicenseNumber());
        assertEquals(driver1.getName(), dto.getName());
        assertEquals(driver1.getPhone(), dto.getPhone());
    }

    @Test
    void getDriverById_ShouldReturnDriver_WhenFound() {
        when(driverRepo.findById(200L)).thenReturn(Optional.of(driver1));

        DriverDto result = service.getDriverById(200L);

        assertEquals("LIC1001", result.getLicenseNumber());
    }

    @Test
    void getDriverById_ShouldThrowException_WhenNotFound() {
        when(driverRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.getDriverById(999L));
    }

    @Test
    void getDriverById_ShouldReturnAddressId() {
        when(driverRepo.findById(200L)).thenReturn(Optional.of(driver1));

        DriverDto result = service.getDriverById(200L);

        assertEquals(address.getAddressId(), result.getAddressId());
    }

    @Test
    void getDriversByOffice_ShouldReturnFilteredList() {
        when(driverRepo.findByOffice_OfficeId(1L)).thenReturn(Arrays.asList(driver1, driver2));

        List<DriverDto> result = service.getDriversByOffice(1L);

        assertEquals(2, result.size());
        assertTrue(result.stream().allMatch(d -> d.getOfficeId().equals(1L)));
    }

    @Test
    void getDriversByOffice_ShouldReturnEmpty_WhenNone() {
        when(driverRepo.findByOffice_OfficeId(5L)).thenReturn(Collections.emptyList());

        List<DriverDto> result = service.getDriversByOffice(5L);

        assertTrue(result.isEmpty());
    }

    @Test
    void getDriversByOffice_ShouldMapName() {
        when(driverRepo.findByOffice_OfficeId(1L)).thenReturn(List.of(driver2));

        List<DriverDto> result = service.getDriversByOffice(1L);
        assertEquals("Jane Smith", result.get(0).getName());
    }

    @Test
    void getDriversByName_ShouldReturnMatchingDrivers() {
        when(driverRepo.findByNameContainingIgnoreCase("John"))
                .thenReturn(List.of(driver1));

        List<DriverDto> result = service.getDriversByName("John");

        assertEquals(1, result.size());
        assertTrue(result.get(0).getName().contains("John"));
    }

    @Test
    void getDriversByName_ShouldReturnEmpty_WhenNoMatch() {
        when(driverRepo.findByNameContainingIgnoreCase("Unknown"))
                .thenReturn(Collections.emptyList());

        List<DriverDto> result = service.getDriversByName("Unknown");

        assertTrue(result.isEmpty());
    }

    @Test
    void getDriversByName_ShouldBeCaseInsensitive() {
        when(driverRepo.findByNameContainingIgnoreCase("jane"))
                .thenReturn(List.of(driver2));

        List<DriverDto> result = service.getDriversByName("jane");

        assertEquals(1, result.size());
        assertEquals("Jane Smith", result.get(0).getName());
    }

    @Test
    void getDriverByLicenseNumber_ShouldReturnDriver_WhenFound() {
        when(driverRepo.findByLicenseNumber("LIC1001")).thenReturn(Optional.of(driver1));

        DriverDto result = service.getDriverByLicenseNumber("LIC1001");

        assertEquals("LIC1001", result.getLicenseNumber());
    }

    @Test
    void getDriverByLicenseNumber_ShouldThrowException_WhenNotFound() {
        when(driverRepo.findByLicenseNumber("INVALID")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getDriverByLicenseNumber("INVALID"));
    }

    @Test
    void getDriverByLicenseNumber_ShouldReturnPhone() {
        when(driverRepo.findByLicenseNumber("LIC1001")).thenReturn(Optional.of(driver1));

        DriverDto result = service.getDriverByLicenseNumber("LIC1001");

        assertEquals("1234567890", result.getPhone());
    }

    @Test
    void getDriverByPhone_ShouldReturnDriver_WhenFound() {
        when(driverRepo.findByPhone("1234567890")).thenReturn(Optional.of(driver1));

        DriverDto result = service.getDriverByPhone("1234567890");

        assertEquals("1234567890", result.getPhone());
    }

    @Test
    void getDriverByPhone_ShouldThrowException_WhenNotFound() {
        when(driverRepo.findByPhone("0000")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getDriverByPhone("0000"));
    }

    @Test
    void getDriverByPhone_ShouldReturnLicenseNumber() {
        when(driverRepo.findByPhone("1234567890")).thenReturn(Optional.of(driver1));

        DriverDto result = service.getDriverByPhone("1234567890");

        assertEquals("LIC1001", result.getLicenseNumber());
    }

    @Test
    void addDriver_ShouldSaveAndReturnDto() {
        DriverDto input = new DriverDto();
        input.setLicenseNumber("LIC999");
        input.setName("New Driver");
        input.setPhone("1111111111");
        input.setOfficeId(1L);
        input.setAddressId(10L);

        when(officeRepo.findById(1L)).thenReturn(Optional.of(office));
        when(addressRepo.findById(10L)).thenReturn(Optional.of(address));
        when(driverRepo.save(any(Driver.class))).thenAnswer(inv -> {
            Driver d = inv.getArgument(0);
            d.setDriverId(300L);
            return d;
        });

        DriverDto result = service.addDriver(input);

        assertEquals("LIC999", result.getLicenseNumber());
        assertEquals(1L, result.getOfficeId());
        assertEquals(10L, result.getAddressId());
        verify(driverRepo).save(any(Driver.class));
    }

    @Test
    void addDriver_ShouldThrowException_WhenOfficeNotFound() {
        DriverDto input = new DriverDto();
        input.setOfficeId(99L);
        when(officeRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.addDriver(input));
    }

    @Test
    void addDriver_ShouldThrowException_WhenAddressNotFound() {
        DriverDto input = new DriverDto();
        input.setAddressId(99L);
        when(addressRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.addDriver(input));
    }

    @Test
    void updateDriver_ShouldUpdateExistingDriver() {
        DriverDto update = new DriverDto();
        update.setLicenseNumber("UPDLIC");
        update.setName("Updated Name");
        update.setPhone("2222222222");
        update.setOfficeId(1L);
        update.setAddressId(10L);

        when(driverRepo.findById(200L)).thenReturn(Optional.of(driver1));
        when(officeRepo.findById(1L)).thenReturn(Optional.of(office));
        when(addressRepo.findById(10L)).thenReturn(Optional.of(address));
        when(driverRepo.save(any(Driver.class))).thenReturn(driver1);

        DriverDto result = service.updateDriver(200L, update);

        assertEquals("UPDLIC", result.getLicenseNumber());
        assertEquals("Updated Name", result.getName());
        assertEquals("2222222222", result.getPhone());
        verify(driverRepo).save(driver1);
    }

    @Test
    void updateDriver_ShouldThrowException_WhenDriverNotFound() {
        when(driverRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateDriver(999L, new DriverDto()));
    }

    @Test
    void updateDriver_ShouldThrowException_WhenOfficeNotFound() {
        DriverDto update = new DriverDto();
        update.setOfficeId(99L);
        when(driverRepo.findById(200L)).thenReturn(Optional.of(driver1));
        when(officeRepo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateDriver(200L, update));
    }

    @Test
    void deleteDriver_ShouldDelete_WhenDriverExists() {
        when(driverRepo.findById(200L)).thenReturn(Optional.of(driver1));
        doNothing().when(driverRepo).delete(driver1);

        assertDoesNotThrow(() -> service.deleteDriver(200L));
        verify(driverRepo).delete(driver1);
    }

    @Test
    void deleteDriver_ShouldThrowException_WhenDriverNotFound() {
        when(driverRepo.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.deleteDriver(999L));
    }

    @Test
    void deleteDriver_ShouldOnlyCallDeleteOnce() {
        when(driverRepo.findById(200L)).thenReturn(Optional.of(driver1));
        service.deleteDriver(200L);
        verify(driverRepo, times(1)).delete(driver1);
    }
}