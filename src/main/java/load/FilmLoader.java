package load;

import java.io.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Film;
import com.fasterxml.jackson.databind.JsonNode;

public class FilmLoader {
    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/";
    private static final String API_KEY = "bfe97887-f460-439e-85fd-68410221af94";
    private static final ConnectorAPI CONNECTOR_TO_API = new ConnectorAPI(API_URL, API_KEY);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public Film loadFilm(int filmId) throws IOException {
        Film film = null;
        String endpoint = String.valueOf(filmId);
        CONNECTOR_TO_API.connectToAPI(endpoint);
        JsonNode jsonNode = CONNECTOR_TO_API.getJsonResponse();

        try {
            film = objectMapper.readValue(jsonNode.toString(), Film.class);
            System.out.println(film);
        } catch (JsonProcessingException e) {
            System.out.println("Error reading json: " + e.getMessage());
        }

        return film;
    }
}