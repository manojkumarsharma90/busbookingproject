package com.busbooking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Address;

import java.util.List;

@Repository
public interface AddressRepo extends JpaRepository<Address, Long> {
	List<Address> findByCityIgnoreCase(String city);
}
