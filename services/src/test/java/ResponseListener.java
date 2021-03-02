import services.handler.ApiResponse;
import services.handler.RequestHandler;
import services.handler.RequestHandlerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

/**
 * Listens for response from API calls
 */
public class ResponseListener implements Runnable {
    private final BlockingQueue<ApiResponse> appQueue;
    /**
     * RequestHandler needed if a service requires further API calls.
     */
    private final RequestHandler handler;
    private boolean running = true;

    public ResponseListener(BlockingQueue<ApiResponse> appQueue) {
        this.appQueue = appQueue;
        handler = RequestHandlerFactory.instance();
    }

    public synchronized void stop() {
        running = false;
        notifyAll();
    }

    private synchronized boolean isRunning() {
        return running;
    }

    public synchronized void run() {
        while (isRunning()) {
            if (!appQueue.isEmpty()) {
                ApiResponse response = appQueue.poll();
                String speechResponse = response.getResponse();
                String serviceName = response.getName();
                HashMap<String, Object> metadata = response.metadata();
                System.out.println("Response from " + serviceName + " API: ");
                System.out.println("---------------------------------------------------------");
                System.out.println(speechResponse);
                HashMap<String, String> params = new HashMap<>();
                // Some services require further operation
                switch (serviceName) {
                    case "Nearest Transport":
                        System.out.println("[APP] Must return stations or bus stops at these coordinates:");
                        ArrayList<Double[]> locations = (ArrayList<Double[]>) metadata.get("locations");
                        for (Double[] location : locations) {
                            System.out.println(Arrays.toString(location));
                        }
                        break;
                    case "Transport Search":
                        System.out.println("[APP]: Must return station or bus stop at these coordinates:");
                        Double[] location = (Double[]) metadata.get("location");
                        System.out.println(Arrays.toString(location));
                        break;
                    case "Recipe":
                        System.out.println("[APP]: Perform extra api request to get recipe information if the user says yes.");
                        System.out.println("[APP]: Also show image, stored in metadata, in app.");
                        int recipeID = (int) metadata.get("recipe-id");
                        System.out.println("[APP]: Recipe id is: " + recipeID);
                        String imageURL = (String) metadata.get("image");
                        System.out.println("[APP]: Image URL is: " + imageURL);
                        params.put("ID", Integer.toString(recipeID));
                        handler.makeRequest("recipe instructions", params);
                        break;
                    case "Recipe Instructions":
                        ArrayList<String> steps = (ArrayList<String>) metadata.get("steps");
                        if (steps != null) {
                            System.out.println("[APP]: Further API call completed, recipe instructions are as follows (stored in metadata):");
                            for (String step : steps) {
                                System.out.println(step);
                            }
                        }
                        break;
                    case "News":
                        System.out.println("[APP]: Show image and a button which when clicked, takes the user to the news article:");
                        String url = (String) metadata.get("url");
                        String image = (String) metadata.get("image");
                        System.out.println("[APP]: News article url is: " + url);
                        System.out.println("[APP]: Image URL is: " + image);
                        break;
                    default:
                        System.out.println("[APP]: Speech Synthesiser speaks the above output - no further action required");
                }
                System.out.println();
                System.out.println();
            }
        }
    }
}
