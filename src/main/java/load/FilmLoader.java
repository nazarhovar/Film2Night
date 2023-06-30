package load;

import java.io.*;
import java.util.Properties;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Film;
import com.fasterxml.jackson.databind.JsonNode;
import util.PropertiesReader;

public class FilmLoader {
    private static final String CONFIG_FILE = "/app/web/api.properties";
    private static final String API_URL = "api.filmUrl";
    private static final String API_KEY = "api.apiKey";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String apiUrl;
    private final String apiKey;

    public FilmLoader() {
        Properties properties = PropertiesReader.loadProperties(CONFIG_FILE);
        apiUrl = properties.getProperty(API_URL);
        apiKey = properties.getProperty(API_KEY);
    }

    public Film loadFilm(int filmId) throws IOException {
        Film film = null;
        String endpoint = String.valueOf(filmId);
        ConnectorAPI connectorToApi = new ConnectorAPI(apiUrl, apiKey);
        connectorToApi.connectToAPI(endpoint);
        JsonNode jsonNode = connectorToApi.getJsonResponse();

        try {
            film = objectMapper.readValue(jsonNode.toString(), Film.class);
            System.out.println(film);
        } catch (JsonProcessingException e) {
            System.out.println("Error reading json: " + e.getMessage());
        }

        return film;
    }
}