package Load;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import Entities.Film;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class FilmLoader {
    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films{id}";
    private static final String API_KEY = "e6a7be3b-3fc1-4e73-a048-b4bb298621b8";

    public static List<Film> loadFilm() {
        List<Film> films = new ArrayList<>();
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("X-API-KEY",API_KEY);
            connection.connect();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode tree = mapper.readTree(connection.getInputStream());
            for (JsonNode filmNode : tree) {
                Film film = parseFilmFromJSON(filmNode);
                films.add(film);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return films;
    }

    private static Film parseFilmFromJSON(JsonNode filmNode) {
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