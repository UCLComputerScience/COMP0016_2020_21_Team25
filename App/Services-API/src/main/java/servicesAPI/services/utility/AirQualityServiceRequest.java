package servicesAPI.services.utility;

import servicesAPI.services.ServiceRequest;

import java.util.HashMap;

public class AirQualityServiceRequest extends ServiceRequest {
    public AirQualityServiceRequest(HashMap<String, String> payload) {
        super("http://api.waqi.info/feed/{CITY_NAME}/?token={API-Key}",
                "Air Quality", "Utility", "9c5ed50306e746d981333e8a4bc86e9185d39602", payload);
    }

    @Override
    protected String parseOutput(HashMap<String, Object> response) {
        HashMap<String, Object> airData = (HashMap<String, Object>) response.get("data");
        String output = "The air quality today in " + payload.get("CITY_NAME") + " is ";
        int airQuality = (int) airData.get("aqi");
        if (airQuality <= 50) {
            return output + "very good!";
        } else if (airQuality <= 100) {
            return output + "acceptable. However, you may be at risk if you are sensitive to air pollution. Please take care.";
        } else if (airQuality <= 150) {
            return output + "quite low. If you are sensitive to low air quality, you may be at risk; otherwise you are less likely to be affected.";
        } else if (airQuality <= 200) {
            return output + "low. Some members of the general public may experience health effects; members of sensitive groups may experience more serious health effects. Please take care.";
        } else if (airQuality <= 300) {
            return output + "very low, the risk of health effects is increased for everyone in the area. Please take care.";
        } else {
            return output + "dangerously low and everyone will be affected. Local authorities are warning of emergency conditions. Please stay safe.";
        }
    }

    @Override
    protected String handleErrors(HashMap<String, Object> response) {
        return "I'm sorry, I could not find any air quality information for " + payload.get("CITY_NAME") + ".";
    }

    @Override
    protected String getErrorCode(HashMap<String, Object> response) {
        return null;
    }

    @Override
    protected HashMap<String, String> populatePayload() {
        HashMap<String, String> servicePayload = new HashMap<>();
        servicePayload.put("CITY_NAME", "london");
        return servicePayload;
    }
}
