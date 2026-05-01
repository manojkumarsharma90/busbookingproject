package com.busbooking.controllertest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.busbooking.controller.SignUpController;
import com.busbooking.dto.SignUpRequestDto;
import com.busbooking.exception.DuplicateResourceException;
import com.busbooking.service.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(
    controllers = SignUpController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = com.busbooking.security.JwtAuthFilter.class
    )
)
@AutoConfigureMockMvc(addFilters = false)
public class SignUpControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    private SignUpRequestDto validDto() {
        SignUpRequestDto dto = new SignUpRequestDto();
        dto.setUserName("user1");
        dto.setPassword("password");
        dto.setEmail("user1@gmail.com");
        dto.setName("User One");
        dto.setPhoneNo("9999999999");
        dto.setAddress("Street 1");
        dto.setCity("Delhi");
        return dto;
    }

    @Test
    void testSignupSuccess() throws Exception {
        SignUpRequestDto dto = validDto();

        when(authService.signup(any()))
        .thenReturn("User registered successfully");

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testSignupDuplicateUser() throws Exception {
        SignUpRequestDto dto = validDto();

        doThrow(new DuplicateResourceException("Username already exists"))
                .when(authService).signup(any());

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    void testSignupRuntimeException() throws Exception {
        SignUpRequestDto dto = validDto();

        doThrow(new RuntimeException("Something went wrong"))
                .when(authService).signup(any());

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testSignupInvalidRequest() throws Exception {
        SignUpRequestDto dto = new SignUpRequestDto();

        mockMvc.perform(post("/auth/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }
}