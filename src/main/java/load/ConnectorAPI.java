package load;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectorAPI {
    private final String apiUrl;
    private final String apiKey;
    private HttpURLConnection connection;
    private final ObjectMapper objectMapper;

    public ConnectorAPI(String apiUrl, String apiKey) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.objectMapper = new ObjectMapper();
    }

    public void connectToAPI(String endpoint) throws IOException {
        URL url = new URL(apiUrl + endpoint);
        connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("accept", "application/json");
        connection.setRequestProperty("X-API-KEY", apiKey);

        System.out.println("Connected to API.");
    }

    public JsonNode getJsonResponse() throws IOException {
        InputStream inputStream = connection.getInputStream();
        JsonNode jsonNode = objectMapper.readTree(inputStream);

        connection.disconnect();
        inputStream.close();

        return jsonNode;
    }
}
