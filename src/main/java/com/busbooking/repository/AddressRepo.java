package com.busbooking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busbooking.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{
	
	List<Address> findByCityIgnoreCase(String city);
	
	List<Address> findByStateIgnoreCase(String state);

	List<Address> findByCityAndStateIgnoreCase(String city, String state);

	List<Address> findByZipCode(String zipCode);

}
