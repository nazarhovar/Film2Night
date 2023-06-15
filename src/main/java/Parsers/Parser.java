package Parsers;

import Entities.Top250;
import Entities.Film;
import com.fasterxml.jackson.databind.JsonNode;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Parser {

    public static List<Top250> parseTopFromJSON(JsonNode jsonNode) {
        List<Top250> top250List = new ArrayList<>();
        JsonNode filmsNode = jsonNode.get("films");
        if (filmsNode != null && filmsNode.isArray()) {
            for (JsonNode filmNode : filmsNode) {
                int filmId = filmNode.get("filmId").asInt();
                String nameEn = filmNode.get("nameEn").asText();
                Top250 top250 = new Top250();
                top250.setId(filmId);
                top250.setName(nameEn);
                top250List.add(top250);
            }
        }
        System.out.println("Fields Top250 loaded correctly");
        return top250List;
    }

    public static Film parseFilmFromJSON(JsonNode jsonNode) {
        Film film = new Film();
        film.setKinopoiskId(jsonNode.get("kinopoiskId").asInt());
        film.setNameOriginal(jsonNode.get("nameOriginal").asText());
        film.setPosterUrl(jsonNode.get("posterUrl").asText());
        film.setRatingKinopoisk(jsonNode.get("ratingKinopoisk").floatValue());
        film.setRatingKinopoiskVoteCount(jsonNode.get("ratingKinopoiskVoteCount").asInt());
        film.setWebUrl(jsonNode.get("webUrl").asText());
        film.setYear(jsonNode.get("year").asInt());
        film.setFilmLength(jsonNode.get("filmLength").asInt());

        JsonNode countriesNode = jsonNode.get("countries");
        if (countriesNode != null && countriesNode.isArray()) {
            StringBuilder countries = new StringBuilder();
            for (JsonNode countryNode : countriesNode) {
                String country = countryNode.get("country").asText();
                countries.append(country).append(",");
            }
            if (countries.length() > 0) {
                countries.deleteCharAt(countries.length() - 1);
            }
            film.setCountries(Collections.singleton(countries.toString()));
        }

        JsonNode genresNode = jsonNode.get("genres");
        if (genresNode != null && genresNode.isArray()) {
            StringBuilder genres = new StringBuilder();
            for (JsonNode genreNode : genresNode) {
                String genre = genreNode.get("genre").asText();
                genres.append(genre).append(",");
            }
            if (genres.length() > 0) {
                genres.deleteCharAt(genres.length() - 1);
            }
            film.setGenres(Collections.singleton(genres.toString()));
        }

        String lastSyncString = jsonNode.get("lastSync").asText();
        LocalDateTime lastSync = LocalDateTime.parse(lastSyncString, DateTimeFormatter.ISO_DATE_TIME);
        film.setLastSync(Timestamp.valueOf(lastSync));

        return film;
    }
}
