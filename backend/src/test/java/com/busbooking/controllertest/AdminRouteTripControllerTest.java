package com.busbooking.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.busbooking.controller.AdminRouteTripController;
import com.busbooking.dto.RouteResponseDto;
import com.busbooking.dto.TripResponseDto;
import com.busbooking.service.RouteTripService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AdminRouteTripController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminRouteTripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RouteTripService service;

    @Autowired
    private ObjectMapper objectMapper;

    // ================= ROUTES =================

    @Test
    public void testGetAllRoutes_Success() throws Exception {
        when(service.getAllRoutes())
                .thenReturn(List.of(new RouteResponseDto()));

        mockMvc.perform(get("/admin/routes"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllRoutes_Empty() throws Exception {
        when(service.getAllRoutes())
                .thenReturn(List.of());

        mockMvc.perform(get("/admin/routes"))
                .andExpect(status().isOk());
    }

    // ================= ROUTE BY ID =================

    @Test
    public void testGetRouteById_Success() throws Exception {
        when(service.getRouteById(1L))
                .thenReturn(new RouteResponseDto());

        mockMvc.perform(get("/admin/routes/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRouteById_Error() throws Exception {
        when(service.getRouteById(1L))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/admin/routes/1"))
                .andExpect(status().isInternalServerError());
    }

    // ================= TRIPS =================

    @Test
    public void testGetAllTrips_Success() throws Exception {
        when(service.getAllTrips())
                .thenReturn(List.of(new TripResponseDto()));

        mockMvc.perform(get("/admin/trips"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllTrips_Empty() throws Exception {
        when(service.getAllTrips())
                .thenReturn(List.of());

        mockMvc.perform(get("/admin/trips"))
                .andExpect(status().isOk());
    }

    // ================= TRIP BY ID =================

    @Test
    public void testGetTripById_Success() throws Exception {
        when(service.getTripById(1L))
                .thenReturn(new TripResponseDto());

        mockMvc.perform(get("/admin/trips/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTripById_Error() throws Exception {
        when(service.getTripById(1L))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/admin/trips/1"))
                .andExpect(status().isInternalServerError());
    }

    // ================= FILTER =================

    @Test
    public void testGetTripsByFare_Success() throws Exception {
        when(service.getTripsByFare(BigDecimal.valueOf(1000)))
                .thenReturn(List.of(new TripResponseDto()));

        mockMvc.perform(get("/admin/trips/fare/1000"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTripsByFare_Empty() throws Exception {
        when(service.getTripsByFare(BigDecimal.valueOf(1000)))
                .thenReturn(List.of());

        mockMvc.perform(get("/admin/trips/fare/1000"))
                .andExpect(status().isOk());
    }
}