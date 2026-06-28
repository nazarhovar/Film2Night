package com.example.film2night.service;

import com.example.film2night.dto.KinopoiskFilmDto;
import com.example.film2night.dto.KinopoiskVideosDto;
import com.example.film2night.entity.Country;
import com.example.film2night.entity.Film;
import com.example.film2night.entity.Genre;
import com.example.film2night.repository.CountryRepository;
import com.example.film2night.repository.FilmRepository;
import com.example.film2night.repository.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
public class FilmLoadService {

    private static final Logger log = LoggerFactory.getLogger(FilmLoadService.class);

    private final KinopoiskClient kinopoiskClient;
    private final FilmRepository filmRepository;
    private final CountryRepository countryRepository;
    private final GenreRepository genreRepository;

    public FilmLoadService(KinopoiskClient kinopoiskClient, FilmRepository filmRepository,
                           CountryRepository countryRepository, GenreRepository genreRepository) {
        this.kinopoiskClient = kinopoiskClient;
        this.filmRepository = filmRepository;
        this.countryRepository = countryRepository;
        this.genreRepository = genreRepository;
    }

    @Transactional
    public Film loadFilmById(Long id) {
        KinopoiskFilmDto dto = kinopoiskClient.fetchFilm(id);
        boolean blocked = filmRepository.findById(id).map(Film::getIsBlocked).orElse(false);
        Film film = new Film(
                dto.getKinopoiskId(),
                dto.getNameOriginal() != null ? dto.getNameOriginal() : dto.getNameRu(),
                dto.getPosterUrl(),
                dto.getRatingKinopoisk(),
                dto.getRatingKinopoiskVoteCount(),
                dto.getWebUrl(),
                dto.getYear(),
                dto.getFilmLength(),
                LocalDateTime.now(),
                blocked,
                null
        );

        if (dto.getCountries() != null) {
            film.setCountries(dto.getCountries().stream()
                    .map(c -> countryRepository.findByName(c.getCountry())
                            .orElseGet(() -> countryRepository.save(new Country(c.getCountry()))))
                    .collect(Collectors.toSet()));
        }

        if (dto.getGenres() != null) {
            film.setGenres(dto.getGenres().stream()
                    .map(g -> genreRepository.findByName(g.getGenre())
                            .orElseGet(() -> genreRepository.save(new Genre(g.getGenre()))))
                    .collect(Collectors.toSet()));
        }

        try {
            KinopoiskVideosDto videos = kinopoiskClient.fetchVideos(dto.getKinopoiskId());
            if (videos != null && videos.getItems() != null) {
                String trailer = videos.getItems().stream()
                        .filter(v -> "YOUTUBE".equals(v.getSite()))
                        .map(KinopoiskVideosDto.VideoItem::getUrl)
                        .findFirst()
                        .orElse(null);
                film.setTrailerUrl(trailer);
            }
        } catch (Exception e) {
            log.warn("Failed to fetch trailer for film {}: {}", dto.getKinopoiskId(), e.getMessage());
        }

        filmRepository.save(film);
        log.info("Film saved: {} ({})", film.getNameOriginal(), film.getKinopoiskId());
        return film;
    }

    public Film getFilmById(Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    public java.util.List<Long> getStaleFilms() {
        return filmRepository.findStaleFilmIds();
    }
}
