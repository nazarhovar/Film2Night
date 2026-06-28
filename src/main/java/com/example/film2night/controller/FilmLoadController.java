package com.example.film2night.controller;

import com.example.film2night.entity.Film;
import com.example.film2night.service.FilmLoadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/load")
public class FilmLoadController {

    private final FilmLoadService filmLoadService;

    public FilmLoadController(FilmLoadService filmLoadService) {
        this.filmLoadService = filmLoadService;
    }

    @PostMapping("/film/{id}")
    public ResponseEntity<?> loadFilm(@PathVariable Long id) {
        try {
            Film film = filmLoadService.loadFilmById(id);
            return ResponseEntity.ok("Film loaded: " + film.getNameOriginal());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error loading film: " + e.getMessage());
        }
    }

    @GetMapping("/film/{id}")
    public ResponseEntity<?> getFilm(@PathVariable Long id) {
        Film film = filmLoadService.getFilmById(id);
        if (film == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(film);
    }

    @GetMapping("/films/stale")
    public ResponseEntity<?> getStaleFilms() {
        return ResponseEntity.ok(filmLoadService.getStaleFilms());
    }
}
