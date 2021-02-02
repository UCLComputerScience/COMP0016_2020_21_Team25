package servicesAPI.services.entertainment;

import servicesAPI.services.ServiceRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class JokeServiceRequest extends ServiceRequest {
    private static final Random randomiser = new Random();
    private final int MAX_JOKES = 10;

    public JokeServiceRequest(HashMap<String, String> payload) {
        super("https://icanhazdadjoke.com/", "Joke", "Entertainment", "", payload);
    }

    @Override
    public String getURL() {
        String url = super.getURL();
        if (!payload.get("TERM").equals("")) {
            url += "search?term={TERM}&limit=" + MAX_JOKES;
        }
        return url;
    }

    protected String parseOutput(HashMap<String, Object> response) {
        int code = (int) response.get("status");
        if (code == 200) {
            if (payload.get("TERM").equals("")) {
                return (String) response.get("joke");
            }
            ArrayList<HashMap<String, Object>> jokes = (ArrayList<HashMap<String, Object>>) response.get("results");
            if (jokes.size() > 0) {
                int randomIndex = randomiser.nextInt(jokes.size());
                HashMap<String, Object> randomJoke = jokes.get(randomIndex);
                return randomJoke.get("joke").toString();
            }
            return "Sorry, I couldn't find a joke about " + payload.get("TERM") + ".";
        }
        return handleErrors(response);
    }

    protected String handleErrors(HashMap<String, Object> response) {
        String errorCode = getErrorCode(response);
        if (errorCode != null) {
            switch (errorCode) {
                case "403":
                    return "The joke service is refusing to respond to your request.";
                case "404":
                    return "The requested action could not be performed. The joke service has been permanently removed.";
                case "429":
                    return "You are accessing the joke service too frequently, please try again in a little while.";
                case "500":
                    return "The requested action could not be performed, the joke service experienced an unknown error. " +
                            "Please try again.";
                case "523":
                    return "The requested action could not be performed, the joke service may be down or experiencing issues. " +
                            "Please try again in a little while.";
            }
        }
        return "The requested action could not be performed. Please try again.";
    }

    protected String getErrorCode(HashMap<String, Object> response) {
        if (response.containsKey("status")) {
            return response.get("status").toString();
        }
        return null;
    }

    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("TERM", "");
        return servicePayload;
    }
}
