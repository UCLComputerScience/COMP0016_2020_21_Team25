package servicesAPI.serviceHandler;

/**
 * Uses factory pattern to enforce the singleton pattern on the request handler.
 */
public class RequestHandlerFactory {
    private static RequestHandler requestHandler;

    /**
     * Retrieve the instance of the request handler object.
     *
     * @return the instance of the request handler object.
     */
    public static RequestHandler instance() {
        if (requestHandler == null) {
            requestHandler = new RequestHandler();
            new Thread(requestHandler).start();
        }
        return requestHandler;
    }
}
