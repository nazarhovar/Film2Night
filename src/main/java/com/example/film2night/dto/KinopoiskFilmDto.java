package com.example.film2night.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KinopoiskFilmDto {
    @JsonProperty("kinopoiskId")
    private Long kinopoiskId;
    @JsonProperty("nameOriginal")
    private String nameOriginal;
    @JsonProperty("nameRu")
    private String nameRu;
    @JsonProperty("posterUrl")
    private String posterUrl;
    @JsonProperty("ratingKinopoisk")
    private Float ratingKinopoisk;
    @JsonProperty("ratingKinopoiskVoteCount")
    private Integer ratingKinopoiskVoteCount;
    @JsonProperty("webUrl")
    private String webUrl;
    @JsonProperty("year")
    private Integer year;
    @JsonProperty("filmLength")
    private Integer filmLength;
    @JsonProperty("countries")
    private List<CountryDto> countries;
    @JsonProperty("genres")
    private List<GenreDto> genres;

    public Long getKinopoiskId() { return kinopoiskId; }
    public String getNameOriginal() { return nameOriginal; }
    public String getNameRu() { return nameRu; }
    public String getPosterUrl() { return posterUrl; }
    public Float getRatingKinopoisk() { return ratingKinopoisk; }
    public Integer getRatingKinopoiskVoteCount() { return ratingKinopoiskVoteCount; }
    public String getWebUrl() { return webUrl; }
    public Integer getYear() { return year; }
    public Integer getFilmLength() { return filmLength; }
    public List<CountryDto> getCountries() { return countries; }
    public List<GenreDto> getGenres() { return genres; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CountryDto {
        @JsonProperty("country")
        private String country;
        public String getCountry() { return country; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class GenreDto {
        @JsonProperty("genre")
        private String genre;
        public String getGenre() { return genre; }
    }
}
