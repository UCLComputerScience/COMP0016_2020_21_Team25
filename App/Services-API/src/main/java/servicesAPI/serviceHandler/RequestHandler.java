package servicesAPI.serviceHandler;

import servicesAPI.services.*;

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
     * Employs the factory pattern to map a service name to its corresponding service request object.
     *
     * @param serviceName The name of the service.
     * @param data        The payload - data required to complete the API call.
     * @return The ServiceRequest object.
     */
    private ServiceRequest getServiceRequestByName(String serviceName,
                                                   HashMap<String, String> data) {
        switch (serviceName.toLowerCase()) {
            case "dictionary":
                return new DictionaryServiceRequest(data);
            case "joke":
                return new JokeServiceRequest(data);
            case "stocks":
                return new StocksServiceRequest(data);
            case "weather":
                return new WeatherServiceRequest(data);
            default:
                return null;
        }
    }

    /**
     * Performs service API request concurrently in a child thread.
     *
     * @param serviceName The name of the service
     * @param data        The data required to complete the restful API call.
     */
    public void makeRequest(String serviceName, HashMap<String, String> data) {
        ServiceRequest serviceRequest = getServiceRequestByName(serviceName, data);
        if (serviceRequest != null) {
            ApiRequest apiRequest = new ApiRequest(serviceRequest, apiResponseQueue);
            new Thread(apiRequest).start();
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
