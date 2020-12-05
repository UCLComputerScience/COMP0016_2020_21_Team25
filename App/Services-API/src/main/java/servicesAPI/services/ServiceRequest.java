package servicesAPI.services;

import java.util.HashMap;

/*
  Defines a RESTful service request
 */
public abstract class ServiceRequest {
    private final String URL;
    private final String name;
    private final String category;
    protected final String APIKey;
    protected final HashMap<String, String> payload;

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

    // Defines how each service interprets its output from the API
    protected abstract String parseOutput(HashMap<String, Object> response);

    // Wrapper to generalise error handling for a service
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

    protected String replaceParameter(String output, String param, HashMap<String, Object> map) {
        return output.replace("{" + param + "}", map.get(param).toString());
    }

    // Defines how each service interprets error messages from the API
    protected abstract String handleErrors(HashMap<String, Object> response);

    // Each service API will have a different way of representing HTTP error codes
    protected abstract String getErrorCode(HashMap<String, Object> response);

    // Insert default data into the payload if not given to avoid malformed requests
    protected abstract HashMap<String, String> populatePayload();
}
