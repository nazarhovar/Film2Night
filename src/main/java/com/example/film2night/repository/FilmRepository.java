package com.example.film2night.repository;

import com.example.film2night.entity.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface FilmRepository extends JpaRepository<Film, Long> {
    @Query("SELECT f.kinopoiskId FROM Film f WHERE f.lastSync < CURRENT_TIMESTAMP - 2")
    List<Long> findStaleFilmIds();
}
