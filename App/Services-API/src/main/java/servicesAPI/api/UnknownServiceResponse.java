package servicesAPI.api;

public class UnknownServiceResponse extends ServiceResponse {
    public UnknownServiceResponse(String service) {
        super(service, "Unknown service name: \"" + service + "\"", null, HttpCodes.UNKNOWN_SERVICE);
    }
}
