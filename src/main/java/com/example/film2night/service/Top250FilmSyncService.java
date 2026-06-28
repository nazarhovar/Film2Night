package com.example.film2night.service;

import com.example.film2night.entity.Top250Film;
import com.example.film2night.repository.Top250FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Top250FilmSyncService {

    private static final Logger log = LoggerFactory.getLogger(Top250FilmSyncService.class);
    private static final long FILM_DELAY_MS = 2000;

    private final Top250FilmRepository top250Repository;
    private final FilmLoadService filmLoadService;

    public Top250FilmSyncService(Top250FilmRepository top250Repository, FilmLoadService filmLoadService) {
        this.top250Repository = top250Repository;
        this.filmLoadService = filmLoadService;
    }

    public String syncAllToFilms() {
        List<Top250Film> top250 = top250Repository.findAll();
        if (top250.isEmpty()) {
            return "Top250 list is empty. Load Top250 first.";
        }

        int success = 0;
        int failed = 0;

        for (Top250Film item : top250) {
            try {
                filmLoadService.loadFilmById(item.getId().longValue());
                success++;
                log.info("Synced film {}: {}", item.getId(), item.getName());
                Thread.sleep(FILM_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
                log.warn("Failed to sync film {}: {}", item.getId(), e.getMessage());
                failed++;
            }
        }

        String result = String.format("Synced %d/%d films to catalog. Failed: %d", success, top250.size(), failed);
        log.info(result);
        return result;
    }
}
