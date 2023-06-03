package Parsers;

import Daos.Impl.CountryDaoImpl;
import Entities.Top250;
import Entities.Film;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Parser {

    protected static Top250 parseTopFromJSON() {
        Top250 top250 = new Top250();
        return top250;
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
        System.out.println("Fields loaded correctly");

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

        return film;
    }
}
