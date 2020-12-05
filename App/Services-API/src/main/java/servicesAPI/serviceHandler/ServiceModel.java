package servicesAPI.serviceHandler;

import servicesAPI.services.JokeServiceRequest;
import servicesAPI.services.ServiceRequest;
import servicesAPI.services.StocksServiceRequest;
import servicesAPI.services.WeatherServiceRequest;

import java.util.HashMap;

// Performs API requests and returns a string of output to be spoken to the user
public class ServiceModel implements ServiceHandler {
    public String makeRequest(String serviceName,
                              HashMap<String, String> data) {
        ServiceRequest serviceRequest = ServiceMapper.getServiceRequestByName(serviceName, data);
        if (serviceRequest != null) {
            HashMap<String, Object> response = RequestHandler.makeRequest(serviceRequest.getURL(), serviceRequest.getPayload());
            if (response != null) {
                return serviceRequest.parseResponse(response);
            }
        }
        return null;
    }

    // Perform request without formatting output into a string
    public HashMap<String, Object> testRequest(String serviceName,
                                               HashMap<String, String> data) {
        ServiceRequest serviceRequest = ServiceMapper.getServiceRequestByName(serviceName, data);
        if (serviceRequest != null) {
            return RequestHandler.makeRequest(serviceRequest.getURL(), serviceRequest.getPayload());
        }
        return null;
    }

    // Private class to map a service name to its service request object
    private static class ServiceMapper {
        public static ServiceRequest getServiceRequestByName(String serviceName,
                                                             HashMap<String, String> data) {
            switch (serviceName.toLowerCase()) {
                case "weather":
                    return new WeatherServiceRequest(data);
                case "stocks":
                    return new StocksServiceRequest(data);
                case "joke":
                    return new JokeServiceRequest(data);
                default:
                    return null;
            }
        }
    }
}
