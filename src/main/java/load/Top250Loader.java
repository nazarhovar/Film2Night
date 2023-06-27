package load;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Top250;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Top250Loader {
    private static final String API_URL = "https://kinopoiskapiunofficial.tech/api/v2.2/films/top?type=TOP_250_BEST_FILMS";
    private static final String API_KEY = "bfe97887-f460-439e-85fd-68410221af94";
    private static final ConnectorAPI CONNECTOR_TO_API = new ConnectorAPI(API_URL, API_KEY);
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<Top250> loadTop() throws IOException {
        List<Top250> top250 = new ArrayList<>();

        for (int currentPage = 1; currentPage <= 13; currentPage++) {
            String endpoint = "&page=" + currentPage;

            CONNECTOR_TO_API.connectToAPI(endpoint);
            JsonNode jsonNode = CONNECTOR_TO_API.getJsonResponse();

            JsonNode filmsNode = jsonNode.get("films");
            if (filmsNode != null) {
                for (JsonNode filmNode : filmsNode) {
                    Top250 top = objectMapper.readValue(filmNode.toString(), Top250.class);
                    top250.add(top);
                }
            }

            System.out.println("Page " + currentPage + " loaded");

            if (filmsNode == null || filmsNode.isEmpty()) {
                System.out.println("Top250 loaded");
                break;
            }
        }

        return top250;
    }
}
