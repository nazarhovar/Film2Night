package Load;

import Entities.Top250;
import Parsers.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Top250Loader extends Parser {
    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_250_BEST_FILMS&page=1";
    private static final String API_KEY = "e6a7be3b-3fc1-4e73-a048-b4bb298621b8";

    public static List<Top250> loadTop () {
        List<Top250> filmsTop = new ArrayList<>();
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("X-API-KEY",API_KEY);
            connection.connect();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode tree = mapper.readTree(connection.getInputStream());
            JsonNode filmsNode = tree.path("films");
            for (JsonNode filmNode : filmsNode) {
                Top250 top250 = parseTopFromJSON(filmNode);
                filmsTop.add(top250);
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filmsTop;
    }
}
