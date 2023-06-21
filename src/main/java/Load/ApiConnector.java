package Load;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiConnector {
    private final String apiUrl;
    private final String apiKey;

    public ApiConnector(String apiUrl, String apiKey) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
    }

    public JsonNode ConnectToAPI(String endpoint) throws IOException {
        URL url = new URL(apiUrl + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("X-API-KEY", apiKey);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(connection.getInputStream());
    }
}
