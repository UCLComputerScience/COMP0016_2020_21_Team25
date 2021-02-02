package servicesAPI.serviceHandler;

import java.util.HashMap;

/**
 * Decorator class to represent an API response for a service that does not exist.
 */
public class UnknownApiResponse extends ApiResponse {
    /**
     * @param serviceName The name of the (unrecognised) service.
     */
    public UnknownApiResponse(String serviceName) {
        super("Unknown service: " + serviceName);
    }

    @Override
    public String getName() {
        return "Error";
    }

    @Override
    public HashMap<String, Object> metadata() {
        return null;
    }
}
