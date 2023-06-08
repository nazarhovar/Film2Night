package Load;

import Entities.Top250;
import Parsers.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Top250Loader extends Parser {
    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_250_BEST_FILMS";
    private static final String API_KEY = "e6a7be3b-3fc1-4e73-a048-b4bb298621b8";

    public static void loadTop (int page) throws IOException {
    }
}
