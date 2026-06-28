package com.example.film2night.repository;

import com.example.film2night.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {
    Optional<Genre> findByName(String name);
}
