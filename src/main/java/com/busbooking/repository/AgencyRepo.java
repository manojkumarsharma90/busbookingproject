package com.busbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Agency;

@Repository
public interface AgencyRepo extends JpaRepository<Agency, Long> {

}
