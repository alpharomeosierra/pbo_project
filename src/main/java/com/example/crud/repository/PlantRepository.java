package com.example.crud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crud.Model.Plant;

public interface PlantRepository extends JpaRepository<Plant, Long> {
}