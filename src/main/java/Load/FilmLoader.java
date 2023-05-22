package Load;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import Entities.Film;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FilmLoader {
    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/";
    private static final String API_KEY = "e6a7be3b-3fc1-4e73-a048-b4bb298621b8";

    public static List<Film> loadFilm(int filmId) {
        List<Film> films = new ArrayList<Film>();
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");

            ObjectMapper mapper = new ObjectMapper();
            Film film1 = new Film();
            //123456890
            //1213123213123
            //nazar
        } catch (IOException e) {
            e.printStackTrace();
        }
        return films;
    }
}