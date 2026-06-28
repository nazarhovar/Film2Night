package com.example.film2night.repository;

import com.example.film2night.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
    Optional<Country> findByName(String name);
}
