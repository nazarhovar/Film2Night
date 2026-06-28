package com.example.film2night.controller;

import com.example.film2night.entity.Top250Film;
import com.example.film2night.service.Top250FilmSyncService;
import com.example.film2night.service.Top250LoadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/load")
public class Top250Controller {

    private final Top250LoadService top250LoadService;
    private final Top250FilmSyncService top250FilmSyncService;

    public Top250Controller(Top250LoadService top250LoadService, Top250FilmSyncService top250FilmSyncService) {
        this.top250LoadService = top250LoadService;
        this.top250FilmSyncService = top250FilmSyncService;
    }

    @PostMapping("/top250")
    public ResponseEntity<?> loadTop250() {
        try {
            List<Top250Film> films = top250LoadService.loadTop250();
            return ResponseEntity.ok("Top250 loaded: " + films.size() + " films");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error loading Top250: " + e.getMessage());
        }
    }

    @GetMapping("/top250")
    public ResponseEntity<List<Top250Film>> getTop250() {
        return ResponseEntity.ok(top250LoadService.getTop250());
    }

    @PostMapping("/top250/full")
    public ResponseEntity<?> syncTop250ToFilms() {
        try {
            String result = top250FilmSyncService.syncAllToFilms();
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error syncing Top250: " + e.getMessage());
        }
    }
}
