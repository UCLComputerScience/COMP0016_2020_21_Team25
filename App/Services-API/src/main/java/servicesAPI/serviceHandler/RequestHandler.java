package servicesAPI.serviceHandler;

import servicesAPI.services.ServiceRequest;

import java.util.HashMap;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Wrapper class to abstract API calls from the main application.
 */
public class RequestHandler implements Runnable {
    /**
     * How often to check for updates in the API response queue.
     */
    private final int UPDATE_DELAY = 1000;
    private final BlockingQueue<ApiResponse> apiResponseQueue = new LinkedBlockingQueue<>();
    private Queue<String> appQueue;
    private volatile boolean running = true;

    /**
     * Performs service API request concurrently in a child thread.
     *
     * @param serviceName The name of the service
     * @param data        The data required to complete the restful API call.
     */
    public void makeRequest(String serviceName, HashMap<String, String> data) {
        ServiceRequest serviceRequest = ServiceFactory.getServiceRequestByName(serviceName, data);
        if (serviceRequest != null) {
            ApiRequest apiRequest = new ApiRequest(serviceRequest, apiResponseQueue);
            new Thread(apiRequest).start();
        } else {
            appQueue.add("Unknown service: " + serviceName);
        }
    }

    public void setAppQueue(Queue<String> appQueue) {
        this.appQueue = appQueue;
    }

    /**
     * Stops the infinite loop checking for API updates.
     */
    public synchronized void stop() {
        this.running = false;
    }

    private synchronized boolean isRunning() {
        return this.running;
    }

    /**
     * Periodically update the app response queue with the data pushed onto the API response queue.
     */
    public void run() {
        while (isRunning()) {
            while (!apiResponseQueue.isEmpty()) {
                ApiResponse response = apiResponseQueue.poll();
                String speechResponse = response.getResponse();
                appQueue.add(speechResponse);
            }
        }
    }
}
