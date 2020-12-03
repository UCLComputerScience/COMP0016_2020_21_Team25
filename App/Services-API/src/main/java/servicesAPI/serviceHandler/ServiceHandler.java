package servicesAPI.serviceHandler;

import java.util.HashMap;

// Defines interaction between main controller and external servicesAPI.services
public interface ServiceHandler {
    // Make the API request and return output
    String makeRequest(String serviceName, HashMap<String, String> data);

    HashMap<String, Object> testRequest(String serviceName, HashMap<String, String> data);
}
