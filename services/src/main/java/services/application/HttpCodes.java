package services.application;

import org.springframework.http.HttpStatus;

/**
 * Class defining our http response codes
 * Uses the values supplied by Spring but renamed to be more descriptive for our use case.
 */
public class HttpCodes {
    // 2xx - Success
    public static final int VALID = HttpStatus.OK.value();

    // 4xx - Client Errors
    /**
     * Malformed request.
     */
    public static final int BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    /**
     * Authorisation is required, but the sender did not provide any.
     * -- Currently not in use.
     */
    public static final int UNAUTHORISED = HttpStatus.UNAUTHORIZED.value();
    /**
     * User does not have sufficient permissions to access our API.
     */
    public static final int FORBIDDEN = HttpStatus.FORBIDDEN.value();
    /**
     * An unrecognised endpoint (service name).
     * -- Currently not in use
     */
    public static final int UNKNOWN_SERVICE = HttpStatus.NOT_FOUND.value();
    /**
     * The request type in the header was POST.
     */
    public static final int METHOD_NOT_ALLOWED = HttpStatus.METHOD_NOT_ALLOWED.value();

    // 5XX - Server Errors
    /**
     * An error occurred within the service API.
     */
    public static final int INTERNAL_ERROR = HttpStatus.INTERNAL_SERVER_ERROR.value();
    /**
     * The endpoint for a service has been added, but the interaction has not yet been implemented.
     */
    public static final int SERVICE_NOT_IMPLEMENTED = HttpStatus.NOT_IMPLEMENTED.value();
    /**
     * The entire service API interaction is down.
     */
    public static final int API_UNAVAILABLE = HttpStatus.SERVICE_UNAVAILABLE.value();
    /**
     * The request was sent using any protocol other than HTTPS.
     */
    public static final int UNSUPPORTED_PROTOCOL = HttpStatus.HTTP_VERSION_NOT_SUPPORTED.value();

    // Prevents instantiation
    private HttpCodes() {

    }
}
