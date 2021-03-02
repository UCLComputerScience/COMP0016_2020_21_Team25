package services.application;

import java.util.HashMap;

public class UnknownServiceResponse extends ServiceResponse {
    public UnknownServiceResponse(String service) {
        super(service, "Unknown service name: '" + service + "'", new HashMap<>(), HttpCodes.UNKNOWN_SERVICE);
    }
}
