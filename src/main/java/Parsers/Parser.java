package Parsers;

import Entities.Film;
import Entities.Top250;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Collections;
import java.util.Set;

public class Parser {

    protected static Top250 parseTopFromJSON(JsonNode filmNode) {
        int id = filmNode.path("id").asInt();
        String name = filmNode.path("name").asText();
        return new Top250(id, name);
    }

    protected static Film parseFilmFromJSON(JsonNode filmNode) {
        int kinopoiskId = filmNode.path("kinopoiskId").asInt();
        String nameOriginal = filmNode.path("nameOriginal").asText();
        String posterUrl = filmNode.path("posterUrl").asText();
        double ratingKinopoisk = filmNode.path("ratingKinopoisk").asDouble();
        int ratingKinopoiskVoteCount = filmNode.path("ratingKinopoiskVoteCount").asInt();
        String webUrl = filmNode.path("webUrl").asText();
        int year = filmNode.path("year").asInt();
        int filmLength = filmNode.path("filmLength").asInt();
        Set<String> countries = Collections.singleton((filmNode.path("countries").path("country").asText()));
        Set<String> genres = Collections.singleton((filmNode.path("genres").path("genre").asText()));
        String isBlocked = filmNode.path("isBlocked").asText();
        return new Film(kinopoiskId, nameOriginal, posterUrl, ratingKinopoisk,
                ratingKinopoiskVoteCount,webUrl, year,filmLength,countries,genres,isBlocked);
    }
}
