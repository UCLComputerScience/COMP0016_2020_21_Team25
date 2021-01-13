package servicesAPI.serviceHandler;

import servicesAPI.services.JokeServiceRequest;
import servicesAPI.services.ServiceRequest;
import servicesAPI.services.StocksServiceRequest;
import servicesAPI.services.WeatherServiceRequest;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import static java.lang.Thread.sleep;

/**
 * Wrapper class to abstract API calls from the main application.
 */
public class RequestHandler implements Runnable {
    /**
     * How often to check for updates in the API response queue.
     */
    private final int UPDATE_DELAY = 1000;
    private final Queue<ApiResponse> apiResponseQueue = new LinkedList<>();
    private Queue<String> appQueue;
    private volatile boolean running = true;

    /**
     * Employs the factory pattern to map a service name to its
     * corresponding service request object.
     * @param serviceName The name of the service.
     * @param data The payload - data required to complete the API call.
     * @return The ServiceRequest object.
     */
    private ServiceRequest getServiceRequestByName(String serviceName,
                                                          HashMap<String, String> data) {
        switch (serviceName.toLowerCase()) {
            case "weather":
                return new WeatherServiceRequest(data);
            case "stocks":
                return new StocksServiceRequest(data);
            case "joke":
                return new JokeServiceRequest(data);
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

    private void setAppQueue(Queue<String> appQueue) {
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
     * Periodically update the app response queue with
     * the data pushed onto the api response queue.
     */
    public synchronized void run() {
        try {
            while (isRunning()) {
                while (!apiResponseQueue.isEmpty()) {
                    ApiResponse response = apiResponseQueue.poll();
                    String speechResponse = response.getResponse();
                    appQueue.add(speechResponse);
                }
                sleep(UPDATE_DELAY);
            }
        } catch (InterruptedException ignored) {

        }
    }
}
