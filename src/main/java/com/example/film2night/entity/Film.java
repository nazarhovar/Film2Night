package com.example.film2night.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "film")
public class Film {
    @Id
    private Long kinopoiskId;
    @Column(name = "name_origin")
    private String nameOriginal;
    @Column(name = "poster_url")
    private String posterUrl;
    @Column(name = "rating_kinopoisk")
    private Float ratingKinopoisk;
    @Column(name = "rating_kinopoisk_vote_count")
    private Integer ratingKinopoiskVoteCount;
    @Column(name = "web_url")
    private String webUrl;
    @Column(name = "film_year")
    private Integer filmYear;
    @Column(name = "film_length")
    private Integer filmLength;
    @Column(name = "last_sync")
    private LocalDateTime lastSync;
    @Column(name = "is_blocked")
    private Boolean isBlocked;
    @Column(name = "trailer_url")
    private String trailerUrl;
    @ManyToMany
    @JoinTable(
            name = "film_country",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> countries = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    public Film() {}

    public Film(Long kinopoiskId, String nameOriginal, String posterUrl, Float ratingKinopoisk,
                Integer ratingKinopoiskVoteCount, String webUrl, Integer filmYear,
                Integer filmLength, LocalDateTime lastSync, Boolean isBlocked,
                String trailerUrl) {
        this.kinopoiskId = kinopoiskId;
        this.nameOriginal = nameOriginal;
        this.posterUrl = posterUrl;
        this.ratingKinopoisk = ratingKinopoisk;
        this.ratingKinopoiskVoteCount = ratingKinopoiskVoteCount;
        this.webUrl = webUrl;
        this.filmYear = filmYear;
        this.filmLength = filmLength;
        this.lastSync = lastSync;
        this.isBlocked = isBlocked;
        this.trailerUrl = trailerUrl;
    }

    public Long getKinopoiskId() { return kinopoiskId; }
    public String getNameOriginal() { return nameOriginal; }
    public Boolean getIsBlocked() { return isBlocked; }
    public void setTrailerUrl(String trailerUrl) { this.trailerUrl = trailerUrl; }
    public void setCountries(Set<Country> countries) { this.countries = countries; }
    public void setGenres(Set<Genre> genres) { this.genres = genres; }
}
