package com.example.film2night.service;

import com.example.film2night.dto.KinopoiskTop250Dto;
import com.example.film2night.entity.Top250Film;
import com.example.film2night.repository.Top250FilmRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class Top250LoadService {

    private static final Logger log = LoggerFactory.getLogger(Top250LoadService.class);
    private static final long PAGE_DELAY_MS = 1500;

    private final KinopoiskClient kinopoiskClient;
    private final Top250FilmRepository top250Repository;

    public Top250LoadService(KinopoiskClient kinopoiskClient, Top250FilmRepository top250Repository) {
        this.kinopoiskClient = kinopoiskClient;
        this.top250Repository = top250Repository;
    }

    public List<Top250Film> loadTop250() {
        List<Top250Film> allFilms = new ArrayList<>();
        KinopoiskTop250Dto firstPage = kinopoiskClient.fetchTop250Page(1);
        if (firstPage == null || firstPage.getFilms() == null || firstPage.getFilms().isEmpty()) {
            log.warn("No data from first page");
            return allFilms;
        }
        int totalPages = firstPage.getPagesCount() != null ? firstPage.getPagesCount() : 13;
        for (KinopoiskTop250Dto.FilmItem item : firstPage.getFilms()) {
            String name = item.getNameRu() != null ? item.getNameRu() : "Unknown";
            allFilms.add(new Top250Film(item.getFilmId(), name));
        }
        for (int page = 2; page <= totalPages; page++) {
            try { Thread.sleep(PAGE_DELAY_MS); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            KinopoiskTop250Dto dto = kinopoiskClient.fetchTop250Page(page);
            if (dto == null || dto.getFilms() == null || dto.getFilms().isEmpty()) break;
            for (KinopoiskTop250Dto.FilmItem item : dto.getFilms()) {
                String name = item.getNameRu() != null ? item.getNameRu() : "Unknown";
                allFilms.add(new Top250Film(item.getFilmId(), name));
            }
            log.info("Top250 page {}/{} loaded", page, totalPages);
        }
        replaceAll(allFilms);
        log.info("Top250 saved: {} films", allFilms.size());
        return allFilms;
    }

    @Transactional
    protected void replaceAll(List<Top250Film> films) {
        top250Repository.deleteAll();
        top250Repository.saveAll(films);
    }

    public List<Top250Film> getTop250() {
        return top250Repository.findAll();
    }
}
