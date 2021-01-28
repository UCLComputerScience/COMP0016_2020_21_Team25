package servicesAPI.serviceHandler;

import servicesAPI.services.ServiceRequest;

import java.util.HashMap;

/**
 * Represents the result of an API call.
 */
public class ApiResponse {
    /**
     * SpeechResponse is a string representing what the response from API call was.
     * It is a plain English sentence for the speech synthesiser to output.
     */
    private final String speechResponse;
    private final ServiceRequest serviceRequest;

    /**
     * @param serviceRequest is the object used to parse the response into an
     *                       English sentence for the speech synthesiser.
     * @param response       represents the JSON object returned by the API.
     */
    public ApiResponse(ServiceRequest serviceRequest, HashMap<String, Object> response) {
        this.serviceRequest = serviceRequest;
        this.speechResponse = serviceRequest.parseResponse(response);
    }

    public String getResponse() {
        return this.speechResponse;
    }

    public String getName() {
        return serviceRequest.getName();
    }

    public HashMap<String, Object> metadata() {
        return serviceRequest.getMetadata();
    }
}
