package load;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Top250;
import com.fasterxml.jackson.databind.JsonNode;
import util.PropertiesReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Top250Loader {
    private static final String CONFIG_FILE = "/app/web/api.properties";
    private static final String TOP_250_URL = "api.top250Url";
    private static final String API_KEY = "api.apiKey";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final String top250Url;
    private final String apiKey;

    public Top250Loader() {
        Properties properties = PropertiesReader.loadProperties(CONFIG_FILE);
        top250Url = properties.getProperty(TOP_250_URL);
        apiKey = properties.getProperty(API_KEY);
    }

    public List<Top250> loadTop() throws IOException {
        List<Top250> top250 = new ArrayList<>();

        for (int currentPage = 1; currentPage <= 13; currentPage++) {
            String endpoint = "&page=" + currentPage;

            ConnectorAPI connectorToApi = new ConnectorAPI(top250Url, apiKey);
            connectorToApi.connectToAPI(endpoint);
            JsonNode jsonNode = connectorToApi.getJsonResponse();

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
