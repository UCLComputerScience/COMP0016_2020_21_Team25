package servicesAPI.services.utility;

import servicesAPI.services.ServiceRequest;

import java.util.HashMap;

public class AirQualityServiceRequest extends ServiceRequest {
    public AirQualityServiceRequest(HashMap<String, String> payload) {
        super("", "Air Quality", "Utility", "", payload);
    }
    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        return null;
    }
}
