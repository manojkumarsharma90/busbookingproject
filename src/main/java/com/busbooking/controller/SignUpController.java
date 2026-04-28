package com.busbooking.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.busbooking.dto.SignUpRequestDto;
import com.busbooking.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class SignUpController {

	@Autowired
	private AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<Map<String, String>> signup(@Valid @RequestBody SignUpRequestDto dto) {

		authService.signup(dto);

		Map<String, String> response = new HashMap<>();
		response.put("message", "User registered successfully");

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
}
