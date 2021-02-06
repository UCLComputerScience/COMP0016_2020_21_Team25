package servicesAPI.api;

import java.util.Map;

/**
 * Represents the JSON object to return to user
 */
public class ServiceResponse {
    private final String service;
    private final String message;
    private final Map<String, Object> metadata;
    private final int code;

    public ServiceResponse(String service, String message, Map<String, Object> metadata, int code) {
        this.service = service;
        this.message = message;
        this.metadata = metadata;
        this.code = code;
    }

    public String getService() {
        return service;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public int getCode() {
        return code;
    }
}
