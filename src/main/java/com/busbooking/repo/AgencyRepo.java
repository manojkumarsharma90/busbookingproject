package com.busbooking.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Agency;

import java.util.Optional;

@Repository
public interface AgencyRepo extends JpaRepository<Agency, Long> {
	Optional<Agency> findByNameIgnoreCase(String name);
	boolean existsByNameIgnoreCase(String name);
}
