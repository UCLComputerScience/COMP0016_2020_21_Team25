package com.example.fisev2concierge.serviceapis;

import java.util.ArrayList;
import java.util.HashMap;

@SuppressWarnings("unchecked")
public class WeatherServiceRequest extends ServiceRequest {
    private final String[] temperatureParams = new String[]{"temp", "feels_like", "temp_min", "temp_max"};

    public WeatherServiceRequest(HashMap<String, String> requestData) {
        super("https://api.openweathermap.org/data/2.5/weather?q={CITY_NAME},{COUNTRY_CODE}&lang={LANGUAGE}&appid={API-Key}",
                "Weather", "Utility", "a19ed1a7f194054f0458cb07ba18c0c3", requestData);
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
                    return "The weather service could not find the city, " +
                            payload.get("CITY_NAME") + ".";
                case "410":
                    return "The requested action could not be performed. The weather service has been permanently removed.";
                case "429":
                    return "You are accessing the weather service too frequently, please try again in a little while";
                case "500":
                    return "The requested action could not be performed, the weather service experienced an unknown error. " +
                            "Please try again.";
                case "502":
                    return "The weather service returned an incomplete response, please try again.";
                case "503":
                    return "The requested action could not be performed, the weather service may be down or experiencing issues. " +
                            "Please try again in a little while.";
                case "504":
                    return "The requested action could not be performed. The weather service took too long to respond. " +
                            "Please try again in a little while.";
            }
        }
        return "The requested action could not be performed. Please try again";
    }

    protected String getErrorCode(HashMap<String, Object> response) {
        if (response.containsKey("cod")) {
            return (String) response.get("cod");
        }
        return null;
    }

    private String convertTemp(String output, String param, HashMap<String, Object> map) {
        return output.replace("{" + param + "}", kelvinToDegrees((Double) map.get(param)));
    }

    private String kelvinToDegrees(Double temp) {
        return Integer.toString((int) (temp - 273.15));
    }

    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("CITY_NAME", "London");
        servicePayload.put("COUNTRY_CODE", "uk");
        servicePayload.put("LANGUAGE", "en");
        return servicePayload;
    }
}