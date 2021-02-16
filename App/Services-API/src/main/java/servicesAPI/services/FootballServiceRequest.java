package main.java.servicesAPI.services;

import java.util.ArrayList;
import java.util.HashMap;

public class FootballServiceRequest extends ServiceRequest{

    public FootballServiceRequest(HashMap<String, String> requestData) {
        super("https://v3.football.api-sports.io/teams?name={NAME}",
                "Football", "Sports", "", requestData);
    }

    protected String parseOutput(HashMap<String, Object> response) {
        ArrayList<HashMap<String, Object>> weatherData = (ArrayList<HashMap<String, Object>>) response.get("weather");
        HashMap<String, Object> weather = weatherData.get(0);

        HashMap<String, Object> main = (HashMap<String, Object>) response.get("main");
        String output = "The weather in {name} today is {description} with the temperature being {temp} degrees celsius but will probably feel like {feels_like} degrees celsius. The high will be {temp_max} degrees celsius and the low, {temp_min} degrees celsius.";
        output = replaceParameter(output, "name", response);
        output = replaceParameter(output, "description", weather);
        for (String param : temperatureParams) {
            output = convertTemp(output, param, main);
        }
        Double temperature = (Double) main.get("temp");
        if (temperature < 11.0) {
            output += "Don't forget to dress warm today!";
        } else if (temperature > 20.0) {
            output += "It's going to be quite warm today, remember to stay hydrated!";
        }
        return output;
    }

    protected String handleErrors(HashMap<String, Object> response) {
        String errorCode = getErrorCode(response);
        if (errorCode != null) {
            switch (errorCode) {
                // An invalid country code or language is ignored by the API
                case "404":
                    return "Error";
            }
        }
        return "The requested action could not be performed. Please try again.";
    }

    protected String getErrorCode(HashMap<String, Object> response) {
        if (response.containsKey("cod")) {
            return (String) response.get("cod");
        }
        return null;
    }

    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("CITY_NAME", "London");
        servicePayload.put("COUNTRY_CODE", "uk");
        servicePayload.put("LANGUAGE", "en");
        return servicePayload;
    }
    
    
}
