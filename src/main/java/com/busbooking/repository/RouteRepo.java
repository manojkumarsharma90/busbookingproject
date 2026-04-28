package com.busbooking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.busbooking.entity.Route;

import java.util.List;

@Repository
public interface RouteRepo extends JpaRepository<Route, Long> {
	List<Route> findByFromCityIgnoreCaseAndToCityIgnoreCase(String fromCity, String toCity);
}
