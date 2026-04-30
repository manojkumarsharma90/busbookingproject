package com.busbooking.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Agency;

@Repository
public interface AgencyRepo extends JpaRepository<Agency, Long> {
	Optional<Agency> findByNameIgnoreCase(String name);
	boolean existsByNameIgnoreCase(String name);
	Optional<Agency> findByEmailIgnoreCase(String email);
	boolean existsByEmailIgnoreCase(String email);
}
