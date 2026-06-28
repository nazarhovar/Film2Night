package com.example.film2night.repository;

import com.example.film2night.entity.Top250Film;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Top250FilmRepository extends JpaRepository<Top250Film, Integer> {
}
