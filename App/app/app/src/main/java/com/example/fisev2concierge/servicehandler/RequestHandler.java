package com.example.fisev2concierge.servicehandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

// Performs API requests over the internet
public class RequestHandler {
    public static HashMap<String, Object> makeRequest(String URL,
                                                      HashMap<String, String> requestData) {
        HttpURLConnection connection = null;
        URL = formatURL(URL, requestData);
        try {
            connection = setupConnection(URL);
            return getResponse(connection);
        } catch (IOException e) {
            // TODO - Only for debugging
            System.err.println(e.getMessage());
            for (StackTraceElement message : e.getStackTrace()) {
                System.err.println(message);
            }
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    // Set up connection to URL
    private static HttpURLConnection setupConnection(String URL) throws IOException {
        URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Limit maximum response time to 5 seconds
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        // Do not follow the URL through any redirects
        connection.setInstanceFollowRedirects(false);
        return connection;
    }

    // Add named parameters to the URL
    private static String formatURL(String URL, HashMap<String, String> parameters) {
        for (HashMap.Entry<String, String> entry : parameters.entrySet()) {
            URL = URL.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return URL;
    }

    // Read JSON response from API into HashMap
    private static HashMap<String, Object> getResponse(HttpURLConnection connection) throws IOException {
        try {
            return readResponse(connection.getInputStream());
        } catch (IOException e) {
            return readResponse(connection.getErrorStream());
        }
    }

    // Read JSON response from API whether its from the input stream or error stream
    private static HashMap<String, Object> readResponse(InputStream responseStream) throws IOException {
        StringBuilder response = new StringBuilder();
        InputStreamReader inReader = new InputStreamReader(responseStream);
        BufferedReader reader = new BufferedReader(inReader);
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return JSONtoMap(response.toString());
    }

    // Convert Map to JSON
    private static HashMap<String, Object> JSONtoMap(String source) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(source, new TypeReference<HashMap<String, Object>>() {
        });
    }
}
