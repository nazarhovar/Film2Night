package com.example.film2night.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KinopoiskTop250Dto {
    @JsonProperty("films")
    private List<FilmItem> films;
    @JsonProperty("pagesCount")
    private Integer pagesCount;

    public List<FilmItem> getFilms() { return films; }
    public Integer getPagesCount() { return pagesCount; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FilmItem {
        @JsonProperty("filmId")
        private Integer filmId;
        @JsonProperty("nameRu")
        private String nameRu;
        public Integer getFilmId() { return filmId; }
        public String getNameRu() { return nameRu; }
    }
}
