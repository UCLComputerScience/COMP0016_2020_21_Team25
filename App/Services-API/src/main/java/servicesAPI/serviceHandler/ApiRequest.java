package servicesAPI.serviceHandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import servicesAPI.services.ServiceRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Queue;

/**
 * Performs RESTful API requests over the internet.
 */
public class ApiRequest implements Runnable {
    /**
     * Limit maximum response time to 5 seconds.
     */
    private static final int MAX_RESPONSE_TIME = 5000;
    private final String URL;
    private final HashMap<String, String> parameters;
    private final Queue<ApiResponse> responseQueue;
    private final ServiceRequest serviceRequest;

    /**
     * Instantiates a new API request.
     *
     * @param serviceRequest   The service request object storing request parameters
     *                         and the URL, including a method to parse the API response.
     * @param apiResponseQueue The api response queue used to push the API's response
     *                         onto a queue to be fed back to the main application.
     */
    public ApiRequest(ServiceRequest serviceRequest,
                      Queue<ApiResponse> apiResponseQueue) {
        this.URL = serviceRequest.getURL();
        this.parameters = serviceRequest.getPayload();
        this.responseQueue = apiResponseQueue;
        this.serviceRequest = serviceRequest;
    }

    /**
     * Set up a connection to the API's URL.
     *
     * @param URL Where the API is located.
     * @return A URL connection object representing the URL.
     * @throws IOException If the connection could not be established.
     */
    private static HttpURLConnection setupConnection(String URL) throws IOException {
        URL url = new URL(URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        connection.setConnectTimeout(MAX_RESPONSE_TIME);
        connection.setReadTimeout(MAX_RESPONSE_TIME);

        // Do not follow the URL through any redirects
        connection.setInstanceFollowRedirects(false);
        return connection;
    }

    /**
     * Puts parameters into the URL for the API request
     *
     * @param URL        Base URL of the API call
     * @param parameters Data to put into the URL
     * @return URL with parameters inserted
     */
    private static String formatURL(String URL, HashMap<String, String> parameters) {
        for (HashMap.Entry<String, String> entry : parameters.entrySet()) {
            URL = URL.replace("{" + entry.getKey() + "}", entry.getValue());
        }
        return URL;
    }


    /**
     * Read JSON response from API into HashMap regardless of whether it is an error response.
     *
     * @param connection Connection object to read response from
     * @return API response as a HashMap
     */
    private static HashMap<String, Object> getResponse(HttpURLConnection connection) {
        try {
            return readResponse(connection.getInputStream());
        } catch (IOException e) {
            return readResponse(connection.getErrorStream());
        }
    }

    /**
     * Returns the API JSON response as a HashMap
     *
     * @param responseStream Stream to read the response from
     * @return The JSON response from the API
     */
    private static HashMap<String, Object> readResponse(InputStream responseStream) {
        StringBuilder response = new StringBuilder();
        InputStreamReader inReader = new InputStreamReader(responseStream);
        BufferedReader reader = new BufferedReader(inReader);
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return JSONtoMap(response.toString());
        } catch (IOException ignored) {

        }
        return null;
    }


    /**
     * Convert JSON response object to HashMap.
     *
     * @param source String representing the JSON response object.
     * @return The HashMap representation of the JSON response.
     * @throws IOException if no response was returned by the API.
     */
    private static HashMap<String, Object> JSONtoMap(String source) throws IOException {
        if (source.startsWith("[") && source.endsWith("]")) {
            source = source.substring(1, source.length() - 1);
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(source, new TypeReference<>() {
        });
    }

    /**
     * Performs the API request and pushes the response onto the response queue.
     */
    public void run() {
        HttpURLConnection connection = null;
        String formattedURL = formatURL(URL, parameters);
        try {
            connection = setupConnection(formattedURL);
            HashMap<String, Object> responseData = getResponse(connection);
            ApiResponse apiResponse = new ApiResponse(serviceRequest, responseData);
            this.responseQueue.add(apiResponse);
        } catch (IOException e) {
            // TODO - Only for debugging
            System.err.println(e.getMessage());
            for (StackTraceElement message : e.getStackTrace()) {
                System.err.println(message);
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
