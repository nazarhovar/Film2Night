package Load;

import java.io.*;

import Entities.Film;
import Parsers.Parser;
import com.fasterxml.jackson.databind.JsonNode;

public class FilmLoader {
    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/";
    private static final String API_KEY = "e6a7be3b-3fc1-4e73-a048-b4bb298621b8";
    private static final ApiConnector apiConnector = new ApiConnector(API_URL, API_KEY);

    public Film loadFilm(int filmId) throws IOException {
        String endpoint = String.valueOf(filmId);
        JsonNode jsonNode = apiConnector.fetchData(endpoint);
        System.out.println(Parser.parseFilmFromJSON(jsonNode));
        return Parser.parseFilmFromJSON(jsonNode);
    }
}