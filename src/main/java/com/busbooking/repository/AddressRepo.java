package com.busbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.busbooking.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long>{

}
