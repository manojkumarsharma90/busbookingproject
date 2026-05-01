package com.busbooking.controllertest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;

import com.busbooking.controller.LoginController;
import com.busbooking.security.AuthRequest;
import com.busbooking.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthenticationManager authManager;

    @MockitoBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testLoginSuccess_UserRole() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("user1");
        request.setPassword("pass");

        Authentication auth = Mockito.mock(Authentication.class);

        when(authManager.authenticate(Mockito.any())).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(true);

        Collection<? extends GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_USER"));

        when(auth.getAuthorities()).thenAnswer(invocation ->
        List.of(new SimpleGrantedAuthority("ROLE_USER")));

        when(jwtService.generateToken(Mockito.eq("user1"), Mockito.anyList()))
                .thenReturn("token123");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginSuccess_AdminRole() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("admin");
        request.setPassword("pass");

        Authentication auth = Mockito.mock(Authentication.class);

        when(authManager.authenticate(Mockito.any())).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(true);

        Collection<? extends GrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));

        when(auth.getAuthorities()).thenAnswer(invocation ->
        List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        
        when(jwtService.generateToken(Mockito.eq("admin"), Mockito.anyList()))
                .thenReturn("admin-token");

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    public void testLoginFailure_NotAuthenticated() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("user1");
        request.setPassword("wrong");

        Authentication auth = Mockito.mock(Authentication.class);

        when(authManager.authenticate(Mockito.any())).thenReturn(auth);
        when(auth.isAuthenticated()).thenReturn(false);

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testLoginFailure_Exception() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("user1");
        request.setPassword("wrong");

        when(authManager.authenticate(Mockito.any()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }
}