package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Film {
    private int kinopoiskId;
    private String nameOriginal;
    private String posterUrl;
    private Float ratingKinopoisk;
    private int ratingKinopoiskVoteCount;
    private String webUrl;
    private int year;
    private int filmLength;
    private Set<Country> countries = new HashSet<>();
    private Set<Genre> genres = new HashSet<>();
    private Timestamp lastSync;
    private String isBlocked;

    public Film(int kinopoiskId, String nameOriginal, String posterUrl, Float ratingKinopoisk, int ratingKinopoiskVoteCount, String webUrl, int year, int filmLength, Set<Country> countries, Set<Genre> genres, String isBlocked) {
        this.kinopoiskId = kinopoiskId;
        this.nameOriginal = nameOriginal;
        this.posterUrl = posterUrl;
        this.ratingKinopoisk = ratingKinopoisk;
        this.ratingKinopoiskVoteCount = ratingKinopoiskVoteCount;
        this.webUrl = webUrl;
        this.year = year;
        this.filmLength = filmLength;
        this.countries = countries;
        this.genres = genres;
        this.isBlocked = isBlocked;
    }

    public Film() {

    }

    public Timestamp getLastSync() {
        return lastSync;
    }

    public void setLastSync(Timestamp lastSync) {
        this.lastSync = lastSync;
    }

    public String getIsBlocked() {
        return isBlocked;
    }

    public void setIsBlocked(String isBlocked) {
        this.isBlocked = isBlocked;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
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
        return kinopoiskId == film.kinopoiskId &&
                ratingKinopoiskVoteCount == film.ratingKinopoiskVoteCount &&
                year == film.year &&
                filmLength == film.filmLength &&
                nameOriginal.equals(film.nameOriginal) &&
                posterUrl.equals(film.posterUrl) &&
                ratingKinopoisk.equals(film.ratingKinopoisk) &&
                webUrl.equals(film.webUrl) &&
                countries.equals(film.countries) &&
                genres.equals(film.genres) &&
                lastSync.equals(film.lastSync) &&
                isBlocked.equals(film.isBlocked);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kinopoiskId, nameOriginal, posterUrl, ratingKinopoisk, ratingKinopoiskVoteCount, webUrl, year, filmLength, countries, genres, lastSync, isBlocked);
    }

    @Override
    public String toString() {
        return "Film{" +
                "kinopoiskId=" + kinopoiskId +
                ", nameOriginal='" + nameOriginal + '\'' +
                ", posterUrl='" + posterUrl + '\'' +
                ", ratingKinopoisk=" + ratingKinopoisk +
                ", ratingKinopoiskVoteCount=" + ratingKinopoiskVoteCount +
                ", webUrl='" + webUrl + '\'' +
                ", year=" + year +
                ", filmLength=" + filmLength +
                ", countries=" + countries +
                ", genres=" + genres +
                ", lastSync=" + lastSync +
                ", isBlocked='" + isBlocked + '\'' +
                '}';
    }
}
