package servicesAPI.services;

import java.util.HashMap;

/*
  Defines a RESTful service request
 */

/**
 * An object defining the outline for a RESTful API as well as a way of parsing
 * the API's response into an English sentence for the speech synthesiser.
 */
public abstract class ServiceRequest {
    protected final String APIKey;
    protected final HashMap<String, String> payload;
    private final String URL;
    private final String name;
    private final String category;

    /**
     * @param URL      The base URL to make the API call.
     * @param name     The name of the service called.
     * @param category The category the service falls under.
     * @param APIKey   API Key needed to access the service's API.
     * @param payload  Data needed to fill out the API call parameters.
     */
    public ServiceRequest(String URL, String name, String category, String APIKey,
                          HashMap<String, String> payload) {
        this.URL = URL;
        this.name = name;
        this.category = category;
        this.APIKey = APIKey;
        this.payload = populatePayload();
        for (HashMap.Entry<String, String> entry : payload.entrySet()) {
            this.payload.put(entry.getKey(), entry.getValue());
        }
        this.payload.put("API-Key", APIKey);
    }

    public String getURL() {
        return URL;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public HashMap<String, String> getPayload() {
        return payload;
    }

    /**
     * Defines how each service interprets its output from the API.
     * @param response The API call's response.
     * @return An English sentence representing the response.
     */
    protected abstract String parseOutput(HashMap<String, Object> response);


    /**
     * A helper method to generalise error handling for an API call.
     * @param response The data returned by the API.
     * @return An English sentence representing the response.
     */
    public String parseResponse(HashMap<String, Object> response) {
        /* If a NullPointerException occurs, it means the parser could not find
        the requested attribute in the JSON response - this occurs when
        the response is malformed or a HTTP error response is returned */
        try {
            return parseOutput(response);
        } catch (NullPointerException e) {
            return handleErrors(response);
        }
    }

    /**
     * @param output
     * @param param
     * @param map
     * @return
     */
    protected String replaceParameter(String output, String param, HashMap<String, Object> map) {
        return output.replace("{" + param + "}", map.get(param).toString());
    }

    /**
     * Defines how each service interprets error response from the API.
     * @param response The data returned by the API.
     * @return The error response message from the API in the form of a sentence.
     */
    protected abstract String handleErrors(HashMap<String, Object> response);

    // Each service API will have a different way of representing HTTP error codes

    /**
     * Each service API will have a different way of representing
     * HTTP error codes and this method generalises this.
     * @param response The data returned by the API.
     * @return The error code in the API response object.
     */
    protected abstract String getErrorCode(HashMap<String, Object> response);

    /**
     * Inserts default data into the payload if not given to avoid malformed requests.
     * @return The payload with default parameters added.
     */
    protected abstract HashMap<String, String> populatePayload();
}
