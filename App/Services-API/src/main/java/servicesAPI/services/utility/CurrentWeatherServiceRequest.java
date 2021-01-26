package servicesAPI.services.utility;

import java.util.HashMap;

public class CurrentWeatherServiceRequest extends AbstractWeatherServiceRequest {

    public CurrentWeatherServiceRequest(HashMap<String, String> payload) {
        super("Current Weather",
                "https://api.openweathermap.org/data/2.5/weather?q={CITY_NAME},{COUNTRY_CODE}&lang={LANGUAGE}",
                payload);
    }

}