package Entities;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Film {
    private int kinopoiskId;
    private String nameOriginal;
    private String posterUrl;
    private Float ratingKinopoisk;
    private int ratingKinopoiskVoteCount;
    private String webUrl;
    private int year;
    private int filmLength;
    private Set<String> countries = new HashSet<>();
    private Set<String> genres = new HashSet<>();

    public Film(int kinopoiskId, String nameOriginal, String posterUrl, double ratingKinopoisk, int ratingKinopoiskVoteCount, String webUrl, int year, int filmLength, Set<String> countries, Set<String> genres, String isBlocked) {
    }

    public Film() {

    }

    public Set<String> getCountries() {
        return countries;
    }

    public void setCountries(Set<String> countries) {
        this.countries = countries;
    }

    public Set<String> getGenres() {
        return genres;
    }

    public void setGenres(Set<String> genres) {
        this.genres = genres;
    }

    public int getKinopoiskId() {
        return kinopoiskId;
    }

    public void setKinopoiskId(int kinopoiskId) {
        this.kinopoiskId = kinopoiskId;
    }

    public String getNameOriginal() {
        return nameOriginal;
    }

    public void setNameOriginal(String nameOriginal) {
        this.nameOriginal = nameOriginal;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public Float getRatingKinopoisk() {
        return ratingKinopoisk;
    }

    public void setRatingKinopoisk(Float ratingKinopoisk) {
        this.ratingKinopoisk = ratingKinopoisk;
    }

    public int getRatingKinopoiskVoteCount() {
        return ratingKinopoiskVoteCount;
    }

    public void setRatingKinopoiskVoteCount(int ratingKinopoiskVoteCount) {
        this.ratingKinopoiskVoteCount = ratingKinopoiskVoteCount;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFilmLength() {
        return filmLength;
    }

    public void setFilmLength(int filmLength) {
        this.filmLength = filmLength;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return kinopoiskId == film.kinopoiskId && ratingKinopoiskVoteCount == film.ratingKinopoiskVoteCount &&
                year == film.year && filmLength == film.filmLength && Objects.equals(nameOriginal, film.nameOriginal) &&
                Objects.equals(posterUrl, film.posterUrl) && Objects.equals(ratingKinopoisk, film.ratingKinopoisk) &&
                Objects.equals(webUrl, film.webUrl) && Objects.equals(countries, film.countries) &&
                Objects.equals(genres, film.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kinopoiskId, nameOriginal, posterUrl, ratingKinopoisk, ratingKinopoiskVoteCount, webUrl,
                year, filmLength , countries, genres /*, lastSync, isBlocked*/);
    }

    @Override
    public String toString() {
        return "Film{" +

                ", kinopoiskId=" + kinopoiskId +
                ", nameOriginal='" + nameOriginal + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", ratingKinopoisk=" + ratingKinopoisk +
                ", ratingKinopoiskVoteCount=" + ratingKinopoiskVoteCount +
                ", webUrl='" + webUrl + '\'' +
                ", year=" + year +
                ", filmLength=" + filmLength +
                ", countries=" + countries +
                ", genres=" + genres +
                '}';
    }
}
