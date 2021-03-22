package services.application;

import org.springframework.web.bind.annotation.*;
import services.api.AbstractServiceRequest;
import services.handler.ServiceFactory;

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
        ApiLogger.logApiRequest(service, headers, requestParams);
        AbstractServiceRequest serviceRequest = ServiceFactory.getServiceRequestByName(service, requestParams);
        if (serviceRequest != null) {
            ServiceApiRequest apiRequest = new ServiceApiRequest(service, serviceRequest);
            return makeRequest(apiRequest);
        }
        return new UnknownServiceResponse(service);
    }

    private ServiceResponse makeRequest(ServiceApiRequest apiRequest) {
        ServiceResponse response = apiRequest.perform();
        ApiLogger.logApiCall(apiRequest, response);
        return response;
    }
}
