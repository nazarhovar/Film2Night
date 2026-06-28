package com.example.film2night.service;

import com.example.film2night.dto.KinopoiskFilmDto;
import com.example.film2night.dto.KinopoiskTop250Dto;
import com.example.film2night.dto.KinopoiskVideosDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class KinopoiskClient {

    private static final Logger log = LoggerFactory.getLogger(KinopoiskClient.class);
    private static final int MAX_RETRIES = 3;
    private static final int INITIAL_DELAY_MS = 2000;

    private final RestTemplate restTemplate;

    @Value("${api.key}")
    private String apiKey;
    @Value("${api.film-url}")
    private String filmUrl;
    @Value("${api.top250-url}")
    private String top250Url;
    @Value("${api.video-url}")
    private String videoUrl;

    public KinopoiskClient() {
        this.restTemplate = new RestTemplate();
    }

    private HttpEntity<Void> authEntity() {
        var headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        headers.set("accept", "application/json");
        return new HttpEntity<>(headers);
    }

    public KinopoiskFilmDto fetchFilm(Long kinopoiskId) {
        var response = restTemplate.exchange(
                filmUrl + kinopoiskId,
                HttpMethod.GET,
                authEntity(),
                KinopoiskFilmDto.class
        );
        return response.getBody();
    }

    public KinopoiskTop250Dto fetchTop250Page(int page) {
        var entity = authEntity();
        int delayMs = INITIAL_DELAY_MS;
        for (int attempt = 0; ; attempt++) {
            try {
                var response = restTemplate.exchange(
                        top250Url + "&page=" + page,
                        HttpMethod.GET,
                        entity,
                        KinopoiskTop250Dto.class
                );
                return response.getBody();
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode().value() == 429 && attempt < MAX_RETRIES) {
                    log.warn("Rate limited (429) on page {}, retry {}/{} after {}ms", page, attempt + 1, MAX_RETRIES, delayMs);
                    try { Thread.sleep(delayMs); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
                    delayMs *= 2;
                } else {
                    if (attempt >= MAX_RETRIES) {
                        log.error("Failed to fetch Top250 page {} after {} retries", page, MAX_RETRIES);
                    }
                    throw e;
                }
            }
        }
    }

    public KinopoiskVideosDto fetchVideos(Long kinopoiskId) {
        var response = restTemplate.exchange(
                videoUrl + kinopoiskId + "/videos",
                HttpMethod.GET,
                authEntity(),
                KinopoiskVideosDto.class
        );
        return response.getBody();
    }
}
