package com.busbooking.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.busbooking.entity.Route;
public interface RouteRepo extends JpaRepository<Route, Long> {
    List<Route> findByFromCity(String fromCity);
    List<Route> findByToCity(String toCity);
    List<Route> findByFromCityAndToCity(String fromCity, String toCity);
}
