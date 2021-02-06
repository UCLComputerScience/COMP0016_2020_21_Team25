package servicesAPI.api;

import org.springframework.web.bind.annotation.*;
import servicesAPI.serviceHandler.ServiceFactory;
import servicesAPI.services.AbstractServiceRequest;

import java.util.HashMap;
import java.util.Map;

// TODO - Client error responses

/**
 * Contains endpoint logic to perform service request
 */
@RestController
@CrossOrigin
public class ServiceController {

    public HashMap<String, String> toUpperCase(Map<String, String> payload) {
        HashMap<String, String> params = new HashMap<>();
        for (Map.Entry<String, String> entry : payload.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            params.put(key.toUpperCase(), value);
        }
        return params;
    }

    @GetMapping("/{service}")
    public ServiceResponse service(
            @RequestHeader Map<String, String> headers,
            @PathVariable String service,
            @RequestParam Map<String, String> payload) {
        service = service.replace("-", " ");
        HashMap<String, String> requestParams = toUpperCase(payload);
        AbstractServiceRequest serviceRequest = ServiceFactory.getServiceRequestByName(service, requestParams);
        if (serviceRequest != null) {
            ServiceApiRequest apiRequest = new ServiceApiRequest(service, serviceRequest);
            return makeRequest(apiRequest);
        }
        return new UnknownServiceResponse(service);
    }

    private ServiceResponse makeRequest(ServiceApiRequest apiRequest) {
        ServiceResponse response = apiRequest.perform();
        if (ServiceApplication.LOG_API_CALLS) {
            System.out.println("[SYSTEM]: Performing API Request");
            System.out.println("    - Service: " + response.getService());
            System.out.println("    - URL: " + apiRequest.getFormattedURL());
            System.out.println("    - Message: " + response.getMessage());
            System.out.println("    - Metadata: " + response.getMetadata());
            System.out.println("    - Code: " + response.getCode());
        }
        return response;
    }
}
