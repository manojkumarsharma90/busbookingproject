package com.busbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.busbooking.dto.SignUpRequestDto;
import com.busbooking.entity.Address;
import com.busbooking.entity.Customer;
import com.busbooking.entity.User;
import com.busbooking.enums.Role;
import com.busbooking.exception.DuplicateResourceException;
import com.busbooking.repository.AddressRepo;
import com.busbooking.repository.CustomerRepo;
import com.busbooking.repository.UserRepo;

@Service
public class AuthService {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CustomerRepo customerRepo;

	@Autowired
	private AddressRepo addressRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;


	// register a new user
	public String signup(SignUpRequestDto dto) {

		// check username already exists
		if (userRepo.existsByUsername(dto.getUserName())) {
			throw new DuplicateResourceException("Username already exists");
		}

		if (userRepo.existsByEmail(dto.getEmail())) {
			throw new DuplicateResourceException("Email already exists");
		}

		// create address if provided
		Address address = null;
		if (dto.getAddress() != null && dto.getCity() != null) {
			address = new Address();
			address.setAddress(dto.getAddress());
			address.setCity(dto.getCity());
			address.setState(dto.getState() != null ? dto.getState() : "");
			address.setZipCode(dto.getZipCode() != null ? dto.getZipCode() : "");
			address = addressRepo.save(address);
		}

		// save into customer table
		Customer customer = new Customer();
		customer.setName(dto.getName());
		customer.setEmail(dto.getEmail());
		customer.setPhone(dto.getPhoneNo());
		customer.setAddress(address);
		customer = customerRepo.save(customer);

		// save into users table
		User user = new User();
		user.setUsername(dto.getUserName());
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setEmail(dto.getEmail());
		user.setFullName(dto.getName());
		user.setPhone(dto.getPhoneNo());
		user.setRole(Role.USER);
		user.setCustomer(customer);
		userRepo.save(user);

		return "User registered successfully with username " + dto.getUserName();
	}
}
