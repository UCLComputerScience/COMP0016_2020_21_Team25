package services.handler;

import services.api.AbstractServiceRequest;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * Wrapper class to abstract API calls from the main application.
 */
public class RequestHandler {
    private BlockingQueue<ApiResponse> appQueue;

    /**
     * Performs service API request concurrently in a child thread.
     *
     * @param serviceName The name of the service
     * @param payload     The data required to complete the restful API call.
     */
    public void makeRequest(String serviceName, HashMap<String, String> payload) {
        AbstractServiceRequest serviceRequest = ServiceFactory.getServiceRequestByName(serviceName, payload);
        if (serviceRequest != null) {
            ApiRequest apiRequest = new ApiRequest(serviceRequest, appQueue);
            new Thread(apiRequest).start();
        } else {
            ApiResponse nullResponse = new UnknownApiResponse(serviceName);
            appQueue.add(nullResponse);
        }
    }

    public void setAppQueue(BlockingQueue<ApiResponse> appQueue) {
        this.appQueue = appQueue;
    }
}
