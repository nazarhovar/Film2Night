package Load;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import Entities.Film;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import Parsers.Parser;

public class FilmLoader extends Parser{
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
}