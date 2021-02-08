package servicesAPI.api;

import java.util.HashMap;

/**
 * A generic error response object
 */
public class GenericErrorResponse extends ServiceResponse {
    public GenericErrorResponse(String message, int code) {
        super("error", message, new HashMap<>(), code);
    }
}
