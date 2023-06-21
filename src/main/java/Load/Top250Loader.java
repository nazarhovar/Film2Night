package Load;

import Entities.Top250;
import Parsers.Parser;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Top250Loader {
    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_250_BEST_FILMS";
    private static final String API_KEY = "e6a7be3b-3fc1-4e73-a048-b4bb298621b8";
    private static final ApiConnector apiConnector = new ApiConnector(API_URL, API_KEY);

    public static List<Top250> loadTop() throws IOException {
        List<Top250> top250 = new ArrayList<>();

        for (int currentPage = 1; currentPage <= 13; currentPage++) {
            String endpoint = "&page=" + currentPage;
            JsonNode jsonNode = apiConnector.ConnectToAPI(endpoint);
            List<Top250> Top250 = Parser.parseTopFromJSON(jsonNode);
            System.out.println("Page " + currentPage + " loaded");

            if (Top250.isEmpty()) {
                System.out.println("Top250 loaded");
                break;
            }

            top250.addAll(Top250);
        }

        return top250;
    }
}
