package servicesAPI.api;

import servicesAPI.serviceHandler.ApiRequest;
import servicesAPI.services.AbstractServiceRequest;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Performs RESTful API requests over the internet.
 */
public class ServiceApiRequest extends ApiRequest {
    private final String name;
    private final AbstractServiceRequest serviceRequest;
    private final String formattedURL;

    /**
     * Instantiates a new API request.
     *
     * @param serviceRequest The service request object storing request parameters and the URL, including a method to parse the API response.
     */
    public ServiceApiRequest(String name,
                             AbstractServiceRequest serviceRequest) {
        super(serviceRequest, null);
        this.name = name;
        this.serviceRequest = serviceRequest;
        this.formattedURL = formatURL(serviceRequest.getURL(), serviceRequest.getPayload());
    }

    public String getFormattedURL() {
        return formattedURL;
    }

    /**
     * Performs the API request
     */
    public ServiceResponse perform() {
        HttpURLConnection connection = null;
        try {
            connection = setupConnection(formattedURL);
            HashMap<String, Object> responseData = getResponse(connection);
            String speechResponse = serviceRequest.parseResponse(responseData);
            Map<String, Object> metadata = serviceRequest.getMetadata();
            int code = serviceRequest.getCode();
            return new ServiceResponse(name, speechResponse, metadata, code);
        } catch (IOException ignored) {
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return new GenericErrorResponse("Could not complete request.", 500);
    }
}
