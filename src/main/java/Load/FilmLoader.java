package Load;

import java.io.*;

import Entities.Film;
import Parsers.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.HttpURLConnection;
import java.net.URL;

public class FilmLoader {
    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/";
    private static final String API_KEY = "e6a7be3b-3fc1-4e73-a048-b4bb298621b8";

    public Film loadFilm(int filmId) throws IOException {
            URL url = new URL(API_URL + filmId);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("X-API-KEY", API_KEY);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(connection.getInputStream());
        Film film = Parser.parseFilmFromJSON(jsonNode);
        System.out.println(film);

        return film;
    }
}