package com.udacity.vehicles.domain.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
//    @Query("SELECT c.id, c.createdAt, c.modifiedAt, c.condition FROM cars WHERE c.id=:id")
//    String findCarById(Long id);
//
//    @Query("SELECT * FROM cars")
//    List<String> findAllCars();
}
