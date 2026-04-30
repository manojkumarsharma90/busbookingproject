package com.busbooking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.busbooking.entity.User;
import com.busbooking.enums.Role;
import com.busbooking.repository.UserRepo;

@Component
public class DataSeeder implements CommandLineRunner {

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) {
		// Create default admin if not exists
		if (!userRepo.existsByUsername("admin")) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(passwordEncoder.encode("admin123"));
			admin.setEmail("admin@busticketbooking.com");
			admin.setFullName("System Administrator");
			admin.setPhone("9999999999");
			admin.setRole(Role.ADMIN);
			userRepo.save(admin);
			System.out.println(">>> Default admin user created (username: admin, password: admin123)");
		}
	}
}
