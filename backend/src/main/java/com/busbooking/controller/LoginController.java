package com.busbooking.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.dto.LoginResponseDto;
import com.busbooking.security.AuthRequest;
import com.busbooking.security.JwtService;

@RestController
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private JwtService jwtService;

	@PostMapping("/login")
	public LoginResponseDto authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

		Authentication authentication = authManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
		);

		if (authentication.isAuthenticated()) {
			List<String> roles = authentication.getAuthorities()
					.stream()
					.map(GrantedAuthority::getAuthority)
					.toList();

			String token = jwtService.generateToken(authRequest.getUsername(), roles);

			LoginResponseDto dto = new LoginResponseDto();
			dto.setToken(token);
			dto.setMsg("user authenticated");
			dto.setTimestamp(LocalDateTime.now().toString());
			dto.setUserName(authRequest.getUsername());

			String role = roles.get(0).replace("ROLE_", "");
			dto.setRole(role);

			return dto;
		} else {
			throw new UsernameNotFoundException("Invalid user request!");
		}
	}
}
