package servicesAPI.api;

/**
 * A generic error response object
 */
public class GenericErrorResponse extends ServiceResponse {
    public GenericErrorResponse(String message, int code) {
        super("error", message, null, code);
    }
}
