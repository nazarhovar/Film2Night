package Load;

import Entities.Top250;
import Parsers.Parser;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;

public class Top250Loader {
    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_250_BEST_FILMS";
    private static final String API_KEY = "e6a7be3b-3fc1-4e73-a048-b4bb298621b8";
    private static final ApiConnector apiConnector = new ApiConnector(API_URL, API_KEY);

    public static List<Top250> loadTop(int page) throws IOException {
        String endpoint = "&page=" + page;
        JsonNode jsonNode = apiConnector.fetchData(endpoint);
        return Parser.parseTopFromJSON(jsonNode);
    }
}
