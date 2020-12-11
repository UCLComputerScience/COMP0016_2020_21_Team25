package com.example.fisev2concierge.serviceapis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

// TODO - API returns jokes in English - translate to preferred language in the MainController
// No API Key needed for JokeAPI
public class JokeServiceRequest extends ServiceRequest  {
    private final ArrayList<String> categories = new ArrayList<>(Arrays.asList("any", "miscellaneous", "programming",
            "dark", "pun", "spooky", "christmas"));

    public JokeServiceRequest(HashMap<String, String> requestData) {
        super("https://sv443.net/jokeapi/v2/joke/{CATEGORY}?lang=en&blacklistFlags=nsfw,religious,political,racist,sexist"
                , "Joke", "Entertainment", "", requestData);
    }

    protected String parseOutput(HashMap<String, Object> response) {
        if (response.containsKey("joke"))
            return (String) response.get("joke");
        return handleErrors(response);
    }

    protected String handleErrors(HashMap<String, Object> response) {
        String errorCode = getErrorCode(response);
        if (errorCode != null) {
            switch (errorCode) {
                case "106":
                    return getErrorMessage();
                case "403":
                    return "The joke service is refusing to respond to your request.";
                case "404":
                    return "The requested action could not be performed. The joke service has been permanently removed.";
                case "429":
                    return "You are accessing the joke service too frequently, please try again in a little while";
                case "500":
                    return "The requested action could not be performed, the joke service experienced an unknown error. " +
                            "Please try again.";
                case "523":
                    return "The requested action could not be performed, the joke service may be down or experiencing issues. " +
                            "Please try again in a little while.";
            }
        }
        return "The requested action could not be performed. Please try again";
    }

    private String getErrorMessage() {
        String category = payload.get("CATEGORY").toLowerCase();
        if (!categories.contains(category)) {
            return category + " is not a valid joke category. The available categories are any, " +
                    "miscellaneous, programming, dark, pun, spooky and Christmas.";
        }
        return "The requested action could not be performed. Please try again";
    }

    protected String getErrorCode(HashMap<String, Object> response) {
        if (response.containsKey("code")) {
            return response.get("code").toString();
        }
        return null;
    }

    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("CATEGORY", "Any");
        return servicePayload;
    }
}
